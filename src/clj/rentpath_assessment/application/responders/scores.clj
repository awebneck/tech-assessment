(ns rentpath-assessment.application.responders.scores
  (:require [clojure.data.json :as json]
            [rentpath-assessment.kafka.queries.score :as score-queries]))

(defn index [_]
  (let [scores (score-queries/get-all)]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/write-str scores)}))
