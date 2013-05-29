(ns clojureve.test.character
  (:require [clojure.test :refer [use-fixtures deftest is run-tests]]
            [clojureve.test.test-util :refer [load-api-key]]
            [clojureve.account :refer [character-keys]])
  (:use [clojureve.character]))

(def ^:dynamic *char-key* nil)

(use-fixtures :once
  (fn [t]
    (binding [*char-key*
              (first (character-keys (load-api-key)))]
      (t))))

(deftest ^:integration test-account-balance
  (let [balance (account-balance *char-key*)]
    (is (not (nil? (:accountID balance))))
    (is (not (nil? (:accountKey balance))))
    (is (not (nil? (:balance balance))))))

(deftest ^:integration test-asset-list
  (doseq [asset (asset-list *char-key*)]
    (is (:itemID asset))
    (is (:locationID asset))
    (when (:content asset)
      (doseq [contained-item (:content asset)]
        (is (:itemID asset))
        (is (nil? (:locationID contained-item)))))))

(deftest ^:integration test-market-orders
  ;; This is all we can do, because the account used
  ;; may not have any orders.
  (is (seq? (market-orders *char-key*))))

(comment
  (binding [*char-key* (first (character-keys (load-api-key)))]
    (clojure.pprint/pprint (market-orders *char-key*)))
  )

#_(run-tests)
