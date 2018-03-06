(ns rentpath-assessment.kafka.queries.score
  (:require [rentpath-assessment.kafka.streams :as streams]))

(defn get-all []
  (streams/get-all :score))
