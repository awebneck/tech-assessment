(ns rentpath-assessment.kafka.streams
  (:require [rentpath-assessment.config :as config]
            [rentpath-assessment.kafka.stream-topologies.score :as score]
            [franzy.serialization.deserializers :as fdes]
            [franzy.serialization.serializers :as fser]
            [clojure.edn :as edn])
  (:import [org.apache.kafka.streams.kstream KStreamBuilder]
           [org.apache.kafka.streams KafkaStreams StreamsConfig]
           [org.apache.kafka.common.serialization Serdes$StringSerde Serializer Serde]
           [org.apache.kafka.streams.state QueryableStoreTypes]))

(deftype NilTolerantEdnSerializer [serializer]
  Serializer
  (configure [_ configs isKey] (.configure serializer configs isKey))
  (serialize [_ topic data]
    (when data (.serialize serializer topic data)))
  (close [_] (.close serializer)))

(def edn-serializer (NilTolerantEdnSerializer. (fser/edn-serializer)))

(def edn-deserializer (fdes/edn-deserializer))

(deftype EdnSerde []
  Serde
  (configure [_ _ _])
  (close [_])
  (serializer [_]
    edn-serializer)
  (deserializer [_]
    edn-deserializer))

(def ^StreamsConfig streams-config
  (StreamsConfig.
    {StreamsConfig/APPLICATION_ID_CONFIG            (config/get-config :kafka-streams-app-id)
     StreamsConfig/BOOTSTRAP_SERVERS_CONFIG         (config/get-config :bootstrap-servers)
     StreamsConfig/KEY_SERDE_CLASS_CONFIG           Serdes$StringSerde
     StreamsConfig/VALUE_SERDE_CLASS_CONFIG         EdnSerde
     StreamsConfig/CACHE_MAX_BYTES_BUFFERING_CONFIG 0
     StreamsConfig/COMMIT_INTERVAL_MS_CONFIG        100000}))

(defn- build-app [topology-fn source-name store-name]
  (let [builder (KStreamBuilder.)
        topology (topology-fn builder source-name store-name)]
    [topology (KafkaStreams. builder streams-config)]))

(defonce streams
         {:score (build-app score/construct
                            (config/get-config :rpa-events-topic-name)
                            (config/get-config :rpa-aggregate-store-name))})

(defn- get-stream-and-topology [stream-key]
  (stream-key streams))

(defn get-stream-store [stream-key]
  (let [[topology stream] (get-stream-and-topology stream-key)
        state-store-name (.queryableStoreName topology)]
    (.store stream state-store-name (QueryableStoreTypes/keyValueStore))))

(defn get-all [stream-key]
  (let [store (get-stream-store stream-key)]
    (reduce #(assoc %1 (.key %2) (.value %2))
            {}
            (iterator-seq (.all store)))))

(defn start-all []
  (doseq [[_ [_ stream]] streams]
    (.start stream)))

(defn stop-all []
  (doseq [[_ [_ stream]] streams]
    (.close stream)))
