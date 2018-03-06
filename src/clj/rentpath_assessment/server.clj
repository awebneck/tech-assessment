(ns rentpath-assessment.server
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [environ.core :refer [env]]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [franzy.serialization.deserializers :refer [edn-deserializer]]
            [franzy.serialization.serializers :refer [edn-serializer]]
            [rentpath-assessment.kafka.streams :as streams]
            [rentpath-assessment.nrepl :as nrepl]
            [rentpath-assessment.config :as config]
            [rentpath-assessment.event-producer :as event-producer]
            [rentpath-assessment.application.responders.scores :as scores]
            [rentpath-assessment.application.responders.simulator :as simulator]))

(defroutes app-routes
           (GET "/scores" [] scores/index)
           (GET "/simulator" [] simulator/show)
           (POST "/simulator" [] simulator/run)
           (route/not-found "Not Found"))

(defn- init-app []
  (-> app-routes
      (wrap-defaults {:params  {:urlencoded true
                                :multipart  true
                                :nested     true
                                :keywordize true}
                      :proxy   true})))

(defn init []
  (streams/start-all)
  (event-producer/start)
  (nrepl/start))

(defn destroy []
  (streams/stop-all)
  (event-producer/stop)
  (nrepl/stop))

(defn -main []
  (.addShutdownHook
    (Runtime/getRuntime)
    (proxy [Thread] []
      (run []
        (destroy))))
  (init)
  (run-server (init-app) {:port (Integer/parseInt (config/get-config :server-port))}))

