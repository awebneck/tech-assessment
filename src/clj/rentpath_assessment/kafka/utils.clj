(ns rentpath-assessment.kafka.utils
  (:import [org.apache.kafka.streams.kstream Reducer ValueMapper]))

(defmacro reducer [kv & body]
  `(reify Reducer
     (apply [_# ~(first kv) ~(second kv)]
       ~@body)))

(defmacro v-mapper [v & body]
  `(reify ValueMapper
     (apply [_# ~(first v)]
       ~@body)))
