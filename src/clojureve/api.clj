(ns clojureve.api
  (:require [clojure.contrib.http.agent :as http]))

(def *api-server* "http://api.eve-online.com")

;; Construct API URLs [with parameters].
(defn make-url
  ([uri]
     (str *api-server* uri))
  ([uri user-id api-key]
     (str (make-url uri) "?userId=" user-id "&apiKey=" api-key))
  ([uri user-id api-key character-id]
     (str (make-url uri user-id api-key) "&characterId=" character-id)))

(defn- anonymous-call [uri]
  (http/stream (http/http-agent (make-url uri))))

(defn- account-call [uri user-id api-key]
  (http/stream (http/http-agent (make-url uri user-id api-key))))

(defn- character-call [uri user-id api-key character-id]
  (http/stream (http/http-agent (make-url uri user-id api-key character-id))))

;; Anonymous calls. These are all no-args functions.
(def server-status
     (partial anonymous-call "/server/ServerStatus.xml.aspx"))
(def alliance-list
     (partial anonymous-call "/eve/AllianceList.xml.aspx"))
(def certificate-tree
     (partial anonymous-call "/eve/CertificateTree.xml.aspx"))

;; Account calls.
(def character-list
     (partial account-call "/account/CharacterList.xml.aspx"))

;; Character calls.
(def character-sheet
     (partial character-call "/char/CharacterSheet.xml.aspx"))



