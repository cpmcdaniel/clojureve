(ns clojureve.main
  (:gen-class)
  (:require [clojureve.zeromq :as zmq]))

#_(:use [clojureve.setup :as setup ])

(defn -main [& args]
  (zmq/example))
