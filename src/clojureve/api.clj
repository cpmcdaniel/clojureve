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
(def conquerable-station-list
     (partial anonymous-call "/eve/ConquerableStationList.xml.aspx"))
(def error-list
     (partial anonymous-call "/eve/ErrorList.xml.aspx"))
(def faction-war-top-stats
     (partial anonymous-call "/eve/FacWarTopStats.xml.aspx"))
(def ref-types
     (partial anonymous-call "/eve/RefTypes.xml.aspx"))
(def skill-tree
     (partial anonymous-call "/eve/SkillTree.xml.aspx"))
(def faction-war-systems
     (partial anonymous-call "/map/FacWarSystems.xml.aspx"))
(def jumps
     (partial anonymous-call "/map/Jumps.xml.aspx"))
(def kills
     (partial anonymous-call "/map/Kills.xml.aspx"))
(def sovereignty
     (partial anonymous-call "/map/Sovereignty.xml.aspx"))

(defn character-id [ & names ]
  (exec-call
   (make-url "/eve/CharacterID.xml.aspx"
	     ;; TODO - encode each name.
	     {:names (clojure.string/join "," names)})))

(defn character-name [ & ids ]
  (exec-call
   (make-url "/eve/CharacterName.xml.aspx"
	     {:ids (clojure.string/join "," ids)})))



;; Account calls.
(def character-list
     (partial account-call "/account/Characters.xml.aspx"))
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

(defn character-info
  ([character-id]
     (exec-call
      (make-url "/eve/CharacterInfo.xml.aspx"
		{"characterId" character-id})))
  ([user-id api-key character-id]
     (character-call "/eve/CharacterInfo.xml.aspx"
		     user-id api-key character-id)))

(defn factional-war-stats
  ([] (anonymous-call "/eve/FacWarStats.xml.aspx"))
  ([user-id api-key character-id]
     (character-call "/char/FacWarStats.xml.aspx"
		     user-id api-key character-id)))


;; Optional param beforeKillId
(defn kill-log
  ([user-id api-key character-id]
     (character-call "/char/KillLog.xml.aspx"
		     user-id api-key character-id))
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
(defn wallet-journal
  ([user-id api-key character-id]
     (character-call "/char/WalletJournal.xml.aspx"
		     user-id api-key character-id))
  ([user-id api-key character-id before-ref-id]
     (exec-call
      (make-url "/char/WalletJournal.xml.aspx"
		{"userId" user-id
		 "apiKey" api-key
		 "characterId" character-id
		 "beforeRefId" before-ref-id}))))

;; beforeTransId - see wallet-journal about "walking".
(defn wallet-transactions
  ([user-id api-key character-id]
     (character-call "/char/WalletTransactions.xml.aspx"
		     user-id api-key character-id))
  ([user-id api-key character-id before-trans-id]
     (exec-call
      (make-url "/char/WalletTransactions.xml.aspx"
		{"userId" user-id
		 "apiKey" api-key
		 "characterId" character-id
		 "beforeTransId" before-trans-id}))))


;; Corporation calls.
(def corp-account-balance
     (partial character-call "/corp/AccountBalance.xml.aspx"))
(def corp-asset-list
     (partial character-call "/corp/AssetList.xml.aspx"))
(def corp-calendar-event-attendees
     (partial character-call "/corp/CalendarEventAtendees.xml.aspx"))
(def corp-contact-list
     (partial character-call "/corp/ContactList.xml.aspx"))
(def corp-container-log
     (partial character-call "/corp/ContainerLog.xml.aspx"))
(def corp-faction-war-stats
     (partial character-call "/corp/FacWarStats.xml.aspx"))
(def corp-industry-jobs
     (partial character-call "/corp/IndustryJobs.xml.aspx"))
(def corp-kill-log
     (partial character-call "/corp/KillLog.xml.aspx"))
(def corp-market-orders
     (partial character-call "/corp/MarketOrders.xml.aspx"))
(def corp-medals
     (partial character-call "/corp/Medals.xml.aspx"))
(def corp-member-medals
     (partial character-call "/corp/MemberMedals.xml.aspx"))
(def corp-member-security
     (partial character-call "/corp/MemberSecurity.xml.aspx"))
(def corp-member-security-log
     (partial character-call "/corp/MemberSecurityLog.xml.aspx"))
(def corp-member-tracking
     (partial character-call "/corp/MemberTracking.xml.aspx"))
(def corp-standings
     (partial character-call "/corp/Standings.xml.aspx"))
(def corp-outpost-list
     (partial character-call "/corp/OutpostList.xml.aspx"))
(def corp-outpost-service-detail
     (partial character-call "/corp/OutpostServiceDetail.xml.aspx"))
(def corp-starbase-list
     (partial character-call "/corp/StarbaseList.xml.aspx"))
(def corp-shareholders
     (partial character-call "/corp/Shareholders.xml.aspx"))
(def corp-titles
     (partial character-call "/corp/Titles.xml.aspx"))

(defn corp-wallet-journal
  ([user-id api-key character-id account-key]
     (exec-call
      (make-url "/corp/WalletJournal.xml.aspx"
		{"userId" user-id
		 "apiKey" api-key
		 "accountKey" account-key
		 "characterId" character-id})))
  ([user-id api-key character-id account-key before-ref-id]
     (exec-call
      (make-url "/corp/WalletJournal.xml.aspx"
		{"userId" user-id
		 "apiKey" api-key
		 "characterId" character-id
		 "accountKey" account-key
		 "beforeRefId" before-ref-id}))))

(defn corp-wallet-transactions
  ([user-id api-key character-id account-key]
     (exec-call
      (make-url "/corp/WalletTransactions.xml.aspx"
		{"userId" user-id
		 "apiKey" api-key
		 "accountKey" account-key	
		 "characterId" character-id})))
  ([user-id api-key character-id account-key before-trans-id]
     (exec-call
      (make-url "/corp/WalletTransactions.xml.aspx"
		{"userId" user-id
		 "apiKey" api-key
		 "characterId" character-id
		 "accountKey" account-key
		 "beforeTransId" before-trans-id}))))

(defn corporation-sheet
  ([corporation-id]
     (exec-call
      (make-url "/corp/CorporationSheet.xml.aspx"
		{"corporationId" corporation-id})))
  ([user-id api-key character-id]
     (character-call "/corp/CorporationSheet.xml.aspx"
		     user-id api-key character-id)))

(defn corp-starbase-detail
  [user-id api-key character-id item-id]
  (exec-call
   (make-url "/char/StarbaseDetail.xml.aspx"
	     {"userId" user-id
	      "apiKey" api-key
	      "characterId" character-id
	      "itemId" item-id})))

