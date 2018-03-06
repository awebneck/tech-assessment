(ns rentpath-assessment.application.templates.layouts
  (:require [hiccup.def :refer [defhtml]]))

(defhtml main [content]
  [:html5
   [:head]
   [:body content]])