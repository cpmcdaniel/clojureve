(ns clojureve.test.api
  (:require [clojure.xml :as xml])
  (:use [clojureve.api] :reload)
  (:use [clojure.test]))

(defn check-status [f]
  (binding [*api-server* "http://api.testeveonline.com"]
        (is (= 200 (:status (f))))))

(deftest test-make-url
  (is (= (str *api-server* "/foo") (make-url "/foo"))))

(deftest ^:integration test-server-status
  (check-status server-status))

(deftest ^:integration test-alliance-list
  (check-status alliance-list))

(deftest ^:integration test-certificate-tree
  (check-status certificate-tree))

(deftest ^:integration test-certificate-tree
  (check-status certificate-tree))



