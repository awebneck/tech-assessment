(ns rentpath-assessment.application.responders.simulator
  (:require [rentpath-assessment.application.templates.layouts :as l]
            [rentpath-assessment.application.templates.simulator :as t]
            [rentpath-assessment.event-producer :as producer]))

(defn show [_]
  (l/main (t/show)))

(defn run [{{:keys [user-name event number]} :params}]
  (producer/generate-events (Integer/parseInt number) :user-name (not-empty user-name) :event (not-empty event))
  (l/main (t/show true)))
