(ns clojureve.test.account
  (:require [clojure.xml :as xml]
            [clojure.java.io :as io]
            [clojure.test :refer [use-fixtures deftest is run-tests]]
            [clojureve.test.test-util :refer [load-api-key]])
  (:use [clojureve.account])
  (:import [clojureve.api ApiKey]))

(deftest ^:integration test-capsuleers
  (for [capsuleer (capsuleers (load-api-key))]
    (is (not (nil? (:name capsuleer))))))

#_(run-tests)
