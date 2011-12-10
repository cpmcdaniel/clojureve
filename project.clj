(defproject clojureve "1.0.0-SNAPSHOT"
  :description "A Clojure wrapper for the Eve Online API"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
		 [org.apache.camel/camel-http "2.5.0"]
                 [mysql/mysql-connector-java "5.1.18"]
                 [compojure "0.6.5"]
                 [hiccup "0.3.7"]]
  :dev-dependencies [[swank-clojure "1.3.2"]
                     [lein-ring "0.4.6"]]
  :ring {:handler clojureve.core/app}
  :main clojureve.main)
