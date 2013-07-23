(ns user
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :refer [pprint]]
            [clojure.repl :refer :all]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [clojureve.system :as system]
            [clojureve.api :refer :all]
            [clojureve.api-util :refer :all]))

;; Global for convenience at repl.
(def system nil)

(defn init
  "Constructs the current development system (but does not start it)."
  []
  (alter-var-root #'system
                  (constantly (system/system))))

(defn start
  "Starts the current development system (must be initialized first)."
  []
  ;; system/start takes the current value of system as a param.
  (alter-var-root #'system system/start))

(defn stop
  "Shuts down and destroys the current development system."
  []
  (alter-var-root #'system
                  (fn [s] (when s (system/stop s)))))

(defn go
  "Initializes the current development system and starts it."
  []
  (init)
  (start))

(defn reset
  "Stops, reloads namespaces, and starts the system."
  []
  (stop)
  (refresh :after 'user/go))
