(ns clojureve.account
  (:require [clojureve.api :refer [character-list]]
            [clojureve.api-util :refer [extract-rows]]))

(defn capsuleers
  "Returns a sequence of maps containing the capsuleer information for the supplied API key."
  [api-key]
  (extract-rows (character-list api-key)))
