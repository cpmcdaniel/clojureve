(defproject clojureve "1.0.0-SNAPSHOT"
  :description "A Clojure wrapper for the Eve Online API"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
		 [org.apache.camel/camel-http "2.5.0"]]
  :dev-dependencies [[swank-clojure "1.2.1"]]
  :main clojureve.camel)
