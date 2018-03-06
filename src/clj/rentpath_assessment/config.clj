(ns rentpath-assessment.config
  (:require [environ.core :refer [env]]))

(defn get-config [key]
  (env key))
