(ns clojureve.test.account
  (:require [clojure.test :refer [use-fixtures deftest is run-tests]]
            [clojureve.test.test-util :refer [load-api-key]])
  (:use [clojureve.account]))

(def server "https://api.testeveonline.com")

(deftest ^:integration test-character-list
  (doseq [capsuleer (character-list server (load-api-key))]
    (is (not (nil? (:name capsuleer))))))

(deftest ^:integration test-character-keys
  (let [{:keys [keyID vCode] :as api-key} (load-api-key)]
    (doseq [char-key (character-keys server api-key)]
      (is (= keyID (:keyID char-key)))
      (is (= vCode (:vCode char-key)))
      (is (not (nil? (:characterID char-key))))
      (is (= 3 (count char-key))))))

(comment
  (character-list (load-api-key))
  )

#_(run-tests)
