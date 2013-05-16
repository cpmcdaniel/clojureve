(defproject clojureve "1.0.0-SNAPSHOT"
  :description "A Clojure wrapper for the Eve Online API"
  :dependencies [[org.clojure/clojure "1.5.1"]
		 [org.apache.camel/camel-http "2.5.0"]
                 [mysql/mysql-connector-java "5.1.18"]
                 [clj-http "0.7.2"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.3"]
                 [org.zmq/jzmq "3.2.2"]]
  :dev-dependencies [[lein-ring "0.8.5"]]
  :native-path "/usr/lib"
  :ring {:handler clojureve.core/app}
  :main clojureve.main
  :aot [clojureve.main])
