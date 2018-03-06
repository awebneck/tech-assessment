(defproject rentpath-assessment "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.clojure/data.json "0.2.6"]
                 [compojure "1.5.1"]
                 [http-kit "2.2.0"]
                 [ring/ring-defaults "0.2.1"]
                 [environ "1.1.0"]
                 [org.apache.kafka/kafka-streams "1.0.0"]
                 [ymilky/franzy "0.0.1"]
                 [ymilky/franzy-admin "0.0.1"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-environ "1.1.0"]]
  :source-paths ["src/clj", "src/cljc"]
  :main rentpath-assessment.server
  :env {:nrepl-port  "8090"
        :nrepl-bind  "0.0.0.0"
        :server-port "8080"
        :rpa-events-topic-name "github-user-events1"
        :rpa-aggregate-store-name "github-event-score-aggregate-store1"
        :kafka-streams-app-id "github-event-score-app1"
        :bootstrap-servers "127.0.0.1:9092"})
