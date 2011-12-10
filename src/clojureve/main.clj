(ns clojureve.main
  (:use [clojureve.setup :as setup ])
  (:gen-class))

(defn -main [& args]
  (setup/init-db))
