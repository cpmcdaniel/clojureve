(ns clojureve.api
  (:require [clojure.contrib.http.agent :as http])
  (:require [clojure.string]))

(def *api-server* "http://api.eve-online.com")

;; Construct API URLs [with parameters].
(defn make-url
  ([uri]
     (str *api-server* uri))
  ([uri params]
     (str (make-url uri) "?"
	  (clojure.string/join "&"
	   (map (fn [[k v]]
		  (str k "=" v))
		params)))))

(defn- exec-call [uri]
  (http/stream (http/http-agent uri)))

(defn- anonymous-call [uri]
  (exec-call (make-url uri)))

(defn- account-call [uri user-id api-key]
  (exec-call
   (make-url uri {"userId" user-id
		  "apiKey" api-key})))

(defn- character-call [uri user-id api-key character-id]
  (exec-call
   (make-url uri {"userId" user-id
		  "apiKey" api-key
		  "characterId" character-id})))

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
(def account-status
     (partial account-call "/account/AccountStatus.xml.aspx"))

;; Character calls.
(def account-balance
     (partial character-call "/char/AccountBalance.xml.aspx"))
(def asset-list
     (partial character-call "/char/AssetList.xml.aspx"))
(def calendar-event-attendees
     (partial character-call "/car/CalendarEventAttendees.xml.aspx"))
(def character-sheet
     (partial character-call "/char/CharacterSheet.xml.aspx"))
(def contact-list
     (partial character-call "/char/ContactList.xml.aspx"))
(def contact-notifications
     (partial character-call "/char/ContactNotifications.xmlaspx"))
(def factional-warfare-stats
     (partial character-call "/char/FacWarStats.xml.aspx"))
(def industry-jobs
     (partial character-call "/char/IndustryJobs.xml.aspx"))
(def mailing-lists
     (partial character-call "/char/MailingLists.xml.aspx"))
(def mail-messages
     (partial character-call "/char/MailMessages.xml.aspx"))
(def market-orders
     (partial character-call "/char/MarketOrders.xml.aspx"))
(def medals
     (partial character-call "/char/Medals.xml.aspx"))
(def notifications
     (partial character-call "/char/Notifications.xml.aspx"))
(def standings
     (partial character-call "/char/Standings.xml.aspx"))
(def research
     (partial character-call "/char/Research.xml.aspx"))
(def skill-in-training
     (partial character-call "/char/SkillInTraining.xml.aspx"))
(def skill-queue
     (partial character-call "/char/SkillQueue.xml.aspx"))
(def upcoming-calendar-events
     (partial character-call "/char/UpcomingCalendarEvents.xml.aspx"))


;; Optional param beforeKillId
(defn kill-log
  ([user-id api-key character-id]
     (exec-call
      (make-url "/char/KillLog.xml.aspx"
		{"userId" user-id
		 "apiKey" api-key
		 "characterId" character-id})))
  ([user-id api-key character-id before-kill-id]
     (exec-call
      (make-url "/char/KillLog.xml.aspx"
		{"userId" user-id
		 "apiKey" api-key
		 "characterId" character-id
		 "beforeKillId" before-kill-id}))))

;; ids param - comma separated list of messageIds from MailMessages.
(defn mail-bodies
  [user-id api-key character-id message-ids]
  (let [message-ids-param (clojure.string/join "," message-ids)]
    (exec-call
     (make-url "/char/MailBodies.xml.aspx"
	       {"userId" user-id
		"apiKey" api-key
		"characterId" character-id
		"messageIds" message-ids-param}))))

;; beforeRefId - used to walk the journal backwards in time.
(def wallet-journal)

;; beforeTransId - see wallet-journal about "walking".
(def wallet-transactions)



;; Corporation calls.
