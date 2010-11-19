(ns clojureve.test.api
  (:require [clojure.xml :as xml])
  (:use [clojureve.api] :reload)
  (:use [clojure.test]))


(deftest url-construction
  (is (= "http://api.eve-online.com/eve/SkillTree.xml.aspx"
	 (make-url "/eve/SkillTree.xml.aspx"))
      "make-url failed for parameter-less call")
  (is (= "http://api.eve-online.com/account/Characters.xml.aspx?userId=jim&apiKey=ak"
	 (make-url "/account/Characters.xml.aspx" {"userId" "jim" "apiKey" "ak"}))
      "make-url failed for call with parameters"))





