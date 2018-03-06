(ns rentpath-assessment.nrepl
  (:require [clojure.tools.nrepl.server :as nrepl-server]
            [rentpath-assessment.config :as config]))

(defonce server (atom nil))

(defn start []
  (reset! server
          (nrepl-server/start-server
            :port (Integer/parseInt (config/get-config :nrepl-port))
            :bind (config/get-config :nrepl-bind))))

(defn stop []
  (let [s @server]
    (when s
      (nrepl-server/stop-server s))))
