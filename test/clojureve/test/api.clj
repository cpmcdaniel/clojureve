(ns clojureve.test.api
  (:require [clojure.xml :as xml]
            [clojure.java.io :as io]
            [clojure.test :refer [use-fixtures deftest is run-tests]]
            [clojureve.test.test-util :refer [load-api-key]]
            [clojureve.api :refer :all]
            [clojureve.api-util :refer :all])
  (:import [clojureve.api ApiKey]))

(def server "https://api.testeveonline.com")

(defn inspect-body [istream]
  (doseq [line (take 20 (line-seq (io/reader istream)))]
    (println line)))

(defn check-status [f]
  (let [response (f)]
    (is (= 200 (:status response)))
    response))

;; Anonymous calls
(deftest ^:integration test-server-status
  (check-status #(server-status server)))

(deftest ^:integration test-alliance-list
  (check-status #(alliance-list server)))

(deftest ^:integration test-certificate-tree
  (check-status #(certificate-tree server)))

(deftest ^:integration test-error-list
  (check-status #(error-list server)))

(deftest ^:integration test-conquerable-station-list
  (check-status #(conquerable-station-list server)))

(deftest ^:integration test-faction-war-top-stats
  (check-status #(faction-war-top-stats server)))

(deftest ^:integration test-ref-types
  (check-status #(ref-types server)))

(deftest ^:integration test-skill-tree
  (check-status #(skill-tree server)))

(deftest ^:integration test-faction-war-systems
  (check-status #(faction-war-systems server)))

(deftest ^:integration test-jumps
  (check-status #(jumps server)))

(deftest ^:integration test-kills
  (check-status #(kills server)))

(deftest ^:integration test-sovereignty
  (check-status #(sovereignty server)))

(deftest ^:integration test-character-id
  (let [response (check-status #(character-id server "Xygoo" "Jennitar" "RycheOn"))]
    (doseq [row (extract-rows response)]
      (is (not (nil? (:characterID row)))))))

(deftest ^:integration test-character-name
  (let [response (check-status #(character-name server 326142676 437036885))]
    (is (= (->> response
              extract-rows
              (map :name)
              set)
         #{"Jennitar" "RycheOn"}))))

;; Account calls

(deftest ^:integration test-character-list
  (doseq [row (extract-rows
               (check-status
                #(character-list server (load-api-key))))]
    (is (not (nil? (:characterID row))))
    (is (not (nil? (:name row))))
    (is (not (nil? (:corporationID row))))
    (is (not (nil? (:corporationName row))))))

(deftest ^:integration test-account-status
  (is (not
       (nil?
        (->> (account-status server (load-api-key))
             body-seq
             (tag-filter :createDate)
             first
             :content
             (apply str))))))

;; repl testing
#_(run-tests)
