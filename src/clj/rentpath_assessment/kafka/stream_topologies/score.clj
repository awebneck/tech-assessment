(ns rentpath-assessment.kafka.stream-topologies.score
  (:require [rentpath-assessment.scoring :as scoring]
            [rentpath-assessment.kafka.utils :refer [v-mapper reducer]])
  (:import [org.apache.kafka.streams.kstream KStreamBuilder]))

(defn construct [^KStreamBuilder builder source-topic-name aggregate-store-name]
  (-> builder
      (.stream (into-array String [source-topic-name]))
      (.mapValues (v-mapper [event-name]
                    {:event-count 1 :total-score (scoring/score event-name)}))
      .groupByKey
      (.reduce (reducer [agg event-data]
                 (-> agg
                     (update :event-count inc)
                     (update :total-score #(+ % (:total-score event-data)))))
               aggregate-store-name)))
