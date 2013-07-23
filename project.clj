(defproject clojureve "1.0.0-SNAPSHOT"
  :description "A Clojure wrapper for the Eve Online API"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.xml "0.0.7"]
                 [clj-http "0.7.2"]]

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.3"]
                                  [org.clojure/java.classpath "0.2.0"]]}}

  :test-selectors {:default (complement :integration)
                   :integration :integration
                   :all (constantly true)} )
