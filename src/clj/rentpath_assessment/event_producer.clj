(ns rentpath-assessment.event-producer
  (:require [environ.core :refer [env]]
            [rentpath-assessment.config :as config])
  (:import [org.apache.kafka.clients.producer KafkaProducer ProducerRecord]))

(defonce producer (atom nil))


(defn start []
  (reset! producer (KafkaProducer. {"bootstrap.servers" (config/get-config :bootstrap-servers)
                                    "acks"              "all"
                                    "retries"           "0"
                                    "key.serializer"    "org.apache.kafka.common.serialization.StringSerializer"
                                    "value.serializer"  "org.apache.kafka.common.serialization.StringSerializer"})))

(defn stop []
  (.close @producer))

(defn send-event [topic key message]
  (.send @producer (ProducerRecord. topic key message)))

(defn generate-events [n & {:keys [user-name event]}]
  (dotimes [_ n]
    (let [user-name (or user-name (str "dude" (rand-int 10)))
          event (or event (rand-nth (keys rentpath-assessment.scoring/scores)))]
      (send-event (config/get-config :rpa-events-topic-name) user-name event))))
