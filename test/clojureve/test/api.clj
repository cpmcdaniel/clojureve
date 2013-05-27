(ns clojureve.test.api
  (:require [clojure.xml :as xml]
            [clojure.java.io :as io]
            [clojure.test :refer [use-fixtures deftest is run-tests]])
  (:use [clojureve.api]
        [clojureve.api-util])
  (:import [clojureve.api ApiKey]))

(use-fixtures
 :each (fn [t]
         (binding [*api-server* "https://api.testeveonline.com"]
           (t))))

(defn inspect-body [istream]
  (doseq [line (take 20 (line-seq (io/reader istream)))]
    (println line)))

(defn check-status [f]
  (let [response (f)]
    (is (= 200 (:status response)))
    response))

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
  (let [response (check-status #(character-id "Xygoo" "Jennitar" "RycheOn"))]
    (doseq [row (extract-rows response)]
      (is (not (nil? (:characterID row)))))))

(deftest ^:integration test-character-name
  (let [response (check-status #(character-name 326142676 437036885))]
    (is (= (->> response
              extract-rows
              (map :name)
              set)
         #{"Jennitar" "RycheOn"}))))

;; Account calls
;; For this to work, you will need to create a api.edn file in
;; the project test directory. The file should contain a single
;; map:
;; {:keyID "xxxxx" :vCode "yyyyy"}
;; This key should have FULL api access.
(defn load-api-key []
  (read-string (slurp "test/api.edn")))

(deftest ^:integration test-character-list
  (doseq [row (extract-rows
               (check-status
                #(character-list (load-api-key))))]
    (is (not (nil? (:characterID row))))
    (is (not (nil? (:name row))))
    (is (not (nil? (:corporationID row))))
    (is (not (nil? (:corporationName row))))))

(deftest ^:integration test-account-status
  (is (not
       (nil?
        (->> (account-status (load-api-key))
             body-seq
             (tag-filter :createDate)
             first
             :content
             (apply str))))))

;; repl testing
#_(run-tests)
