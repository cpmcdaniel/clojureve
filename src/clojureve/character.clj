(ns clojureve.character
  (:require [clojureve.api :as api]
            [clojureve.api-util :refer [extract-rows body-seq]]))

(defn account-balance
  "Gets account balance for the given character key."
  [character-key]
  (first (extract-rows (api/account-balance character-key))))

(defn- asset-mapper
  "Recursively builds asset maps (with contents for containers)."
  [{:keys [attrs content] :as asset-row}]
  (if (empty? content)
    attrs
    (assoc attrs :content (map asset-mapper (:content (first content))))))

(defn asset-list
  "Returns a list of assets owned by the given character."
  [character-key]
  (->> (api/asset-list character-key)
       body-seq
       (filter (fn [x]
                 (and
                  (= :rowset (:tag x))
                  (= "assets" (:name (:attrs x))))))
       (map :content)
       first
       (map asset-mapper)))

(defn market-orders
  "Returns the market orders for the given character."
  [character-key]
  (extract-rows (api/market-orders character-key)))
