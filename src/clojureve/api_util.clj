(ns clojureve.api-util
  (:require [clojure.data.xml :refer [parse]]))

(defn body-seq
  "Parses the body input stream into a seq of xml elements."
  [{:keys [body]}]
  (if body
    (xml-seq (parse body))
    '()))

(defn row?
  "Predicate function for 'row' elements."
  [x]
  (= :row (:tag x)))

(defn extract-rows
  "For simple responses, filters out the rows and extracts the attribute maps."
  [response]
  (->> response
       body-seq
       (filter row?)
       (map :attrs)))

(defn tag-filter
  "Filters a collection of xml elements by the tag name specified."
  [tag coll]
  (filter #(= tag (:tag %)) coll))

