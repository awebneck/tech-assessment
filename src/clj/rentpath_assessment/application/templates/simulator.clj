(ns rentpath-assessment.application.templates.simulator
  (:require [hiccup.def :refer [defhtml]]
            [rentpath-assessment.scoring :as scoring]))

(defhtml show [& [show-sim-header]]
  (when show-sim-header
    [:p "Simulation run! View aggregate results "
     [:a {:href "/scores" :target "_blank"} "here"]
     "."])
  [:form {:method "post"}
   [:div
    [:label {:for "user-name"} "User Name (random if blank):"]
    [:input {:type "text" :id "user-name" :name "user-name"}]]
   [:div
    [:label {:for "number"} "Number of events to generate:"]
    [:input {:type "text" :id "number" :name "number" :value "20"}]]
   [:div
    [:label {:for "event"} "Event (random if blank):"]
    [:select {:name "event" :id "event"}
     (for [key (cons "" (keys scoring/scores))]
       [:option key])]]
   [:div
    [:input {:type "submit" :value "Simulate!"}]]])
