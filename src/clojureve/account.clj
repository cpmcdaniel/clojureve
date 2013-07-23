(ns clojureve.account
  (:require [clojureve.api :as api]
            [clojureve.api-util :refer [extract-rows]]))

(defn character-list
  "Returns a sequence of maps containing the capsuleer information for the supplied API key."
  [server api-key]
  (extract-rows (api/character-list server api-key)))

(defn character-keys
  "Returns a sequence of maps containing :keyID, :vCode, and :characterID for each character for the given API key."
  [server api-key]
  (map #(assoc api-key :characterID (:characterID %))
       (character-list server api-key)))
