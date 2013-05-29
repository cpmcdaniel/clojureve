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

(deftest ^:integration test-wallet-journal
  (let [journal-entries (wallet-journal *char-key*)]
    (is (seq? journal-entries))
    (when (> (count journal-entries) 6)
      (is (= 4 (count (wallet-journal
                       (assoc *char-key*
                         :fromID (:refID (nth journal-entries 1))
                         :rowCount 4))))))))

(deftest ^:integration test-wallet-transactions
  (let [transactions (wallet-transactions *char-key*)]
    (is (seq? transactions))
    (when (> (count transactions) 6)
      (is (= 4 (count (wallet-transactions
                       (assoc *char-key*
                         :fromID (:refID (nth transactions 1))
                         :rowCount 4))))))))

(comment
  (binding [*char-key* (first (character-keys (load-api-key)))]
    (clojure.pprint/pprint (count (wallet-journal *char-key*))))
  )

#_(run-tests)
