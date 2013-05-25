(ns clojureve.test.api
  (:require [clojure.xml :as xml]
            [clojure.java.io :as io])
  (:use [clojureve.api] :reload)
  (:use [clojure.test]))

(use-fixtures
 :each (fn [t]
         (binding [*api-server* "https://api.testeveonline.com"]
           (t))))

(defn inspect-body [istream]
  (doseq [line (take 20 (line-seq (io/reader istream)))]
    (println line)))

(defn check-status [f]
  (is (= 200 (:status (f)))))

(deftest test-make-url
  (is (= (str *api-server* "/foo") (make-url "/foo"))))


;; Anonymous calls
(deftest ^:integration test-server-status
  (check-status server-status))

(deftest ^:integration test-alliance-list
  (check-status alliance-list))

(deftest ^:integration test-certificate-tree
  (check-status certificate-tree))

(deftest ^:integration test-error-list
  (check-status error-list))

(deftest ^:integration test-conquerable-station-list
  (check-status conquerable-station-list))

(deftest ^:integration test-faction-war-top-stats
  (check-status faction-war-top-stats))

(deftest ^:integration test-ref-types
  (check-status ref-types))

(deftest ^:integration test-skill-tree
  (check-status skill-tree))

(deftest ^:integration test-faction-war-systems
  (check-status faction-war-systems))

(deftest ^:integration test-jumps
  (check-status jumps))

(deftest ^:integration test-kills
  (check-status kills))

(deftest ^:integration test-sovereignty
  (check-status sovereignty))

(deftest ^:integration test-character-id
  (->> (character-id "Xygoo" "Jennitar" "Rycheon")
       body-seq
       (filter #(= :row (:tag %)))
       )
  )



