(ns clojureve.test.api
  (:use [clojureve.api] :reload)
  (:use [clojure.test]))


(deftest url-construction
  (is (= "http://api.eve-online.com/eve/SkillTree.xml.aspx"
	 (make-url "/eve/SkillTree.xml.aspx"))
      "make-url failed for anonymous call")
  (is (= "http://api.eve-online.com/account/Characters.xml.aspx?userId=jim&apiKey=ak"
	 (make-url "/account/Characters.xml.aspx" "jim" "ak"))
      "make-url failed for account call")
  (is (= "http://api.eve-online.com/char/AccountBalance.xml.aspx?userId=jim&apiKey=ak&characterId=1"
	 (make-url "/char/AccountBalance.xml.aspx" "jim" "ak" "1"))
      "make-url failed for character call"))



