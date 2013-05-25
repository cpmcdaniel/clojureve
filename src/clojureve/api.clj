(ns clojureve.api
  (:require [clj-http.client :as http]
            [clojure.string :refer [join]]
            [clojure.data.xml :refer [parse]]))

(def ^:dynamic *api-server* "https://api.eveonline.com")

(defn body-seq [{:keys [body]}]
  (if body
    (xml-seq (parse body))
    '()))

(defn make-url
  "Construct API URLs."
  [path-info]
  (str *api-server* path-info))

(defn- exec-call [path-info params]
  (http/post (make-url path-info)
             {:form-params params
              :as :stream}))

(defn- anonymous-call [path-info]
  (exec-call path-info {}))

;; Anonymous calls. These are all no-args functions.
(def #^{:category "Server" 
	:key-type :none
	:cache-style :modified-short}
  server-status
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
  (exec-call "/eve/CharacterID.xml.aspx"
             ;; TODO - encode each name.
             {:names (clojure.string/join "," names)}))

(defn character-name [ & ids ]
  (exec-call "/eve/CharacterName.xml.aspx"
             {:ids (clojure.string/join "," ids)}))

;; Account calls.
(def character-list
     (partial exec-call "/account/Characters.xml.aspx"))
(def account-status
     (partial exec-call "/account/AccountStatus.xml.aspx"))

;; Character calls.
(def account-balance
     (partial exec-call "/char/AccountBalance.xml.aspx"))
(def asset-list
     (partial exec-call "/char/AssetList.xml.aspx"))
(def calendar-event-attendees
     (partial exec-call "/car/CalendarEventAttendees.xml.aspx"))
(def character-sheet
     (partial exec-call "/char/CharacterSheet.xml.aspx"))
(def contact-list
     (partial exec-call "/char/ContactList.xml.aspx"))
(def contact-notifications
     (partial exec-call "/char/ContactNotifications.xmlaspx"))
(def industry-jobs
     (partial exec-call "/char/IndustryJobs.xml.aspx"))
(def mailing-lists
     (partial exec-call "/char/MailingLists.xml.aspx"))
(def mail-messages
     (partial exec-call "/char/MailMessages.xml.aspx"))
(def market-orders
     (partial exec-call "/char/MarketOrders.xml.aspx"))
(def medals
     (partial exec-call "/char/Medals.xml.aspx"))
(def notifications
     (partial exec-call "/char/Notifications.xml.aspx"))
(def standings
     (partial exec-call "/char/Standings.xml.aspx"))
(def research
     (partial exec-call "/char/Research.xml.aspx"))
(def skill-in-training
     (partial exec-call "/char/SkillInTraining.xml.aspx"))
(def skill-queue
     (partial exec-call "/char/SkillQueue.xml.aspx"))
(def upcoming-calendar-events
     (partial exec-call "/char/UpcomingCalendarEvents.xml.aspx"))

(defn character-info
  ([character-id]
     (exec-call
      (make-url "/eve/CharacterInfo.xml.aspx"
		{"characterId" character-id})))
  ([user-id api-key character-id]
     (exec-call "/eve/CharacterInfo.xml.aspx"
		     user-id api-key character-id)))

(defn factional-war-stats
  ([] (anonymous-call "/eve/FacWarStats.xml.aspx"))
  ([user-id api-key character-id]
     (exec-call "/char/FacWarStats.xml.aspx"
		     user-id api-key character-id)))


;; Optional param beforeKillId
(defn kill-log
  ([user-id api-key character-id]
     (exec-call "/char/KillLog.xml.aspx"
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
     (exec-call "/char/WalletJournal.xml.aspx"
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
     (exec-call "/char/WalletTransactions.xml.aspx"
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
     (partial exec-call "/corp/AccountBalance.xml.aspx"))
(def corp-asset-list
     (partial exec-call "/corp/AssetList.xml.aspx"))
(def corp-calendar-event-attendees
     (partial exec-call "/corp/CalendarEventAtendees.xml.aspx"))
(def corp-contact-list
     (partial exec-call "/corp/ContactList.xml.aspx"))
(def corp-container-log
     (partial exec-call "/corp/ContainerLog.xml.aspx"))
(def corp-faction-war-stats
     (partial exec-call "/corp/FacWarStats.xml.aspx"))
(def corp-industry-jobs
     (partial exec-call "/corp/IndustryJobs.xml.aspx"))
(def corp-kill-log
     (partial exec-call "/corp/KillLog.xml.aspx"))
(def corp-market-orders
     (partial exec-call "/corp/MarketOrders.xml.aspx"))
(def corp-medals
     (partial exec-call "/corp/Medals.xml.aspx"))
(def corp-member-medals
     (partial exec-call "/corp/MemberMedals.xml.aspx"))
(def corp-member-security
     (partial exec-call "/corp/MemberSecurity.xml.aspx"))
(def corp-member-security-log
     (partial exec-call "/corp/MemberSecurityLog.xml.aspx"))
(def corp-member-tracking
     (partial exec-call "/corp/MemberTracking.xml.aspx"))
(def corp-standings
     (partial exec-call "/corp/Standings.xml.aspx"))
(def corp-outpost-list
     (partial exec-call "/corp/OutpostList.xml.aspx"))
(def corp-outpost-service-detail
     (partial exec-call "/corp/OutpostServiceDetail.xml.aspx"))
(def corp-starbase-list
     (partial exec-call "/corp/StarbaseList.xml.aspx"))
(def corp-shareholders
     (partial exec-call "/corp/Shareholders.xml.aspx"))
(def corp-titles
     (partial exec-call "/corp/Titles.xml.aspx"))

(defn corp-wallet-journal
  ([params account-key]
     (exec-call "/corp/WalletJournal.xml.aspx"
                (assoc params "accountKey" account-key)))
  ([params account-key before-ref-id]
     (exec-call "/corp/WalletJournal.xml.aspx"
                (assoc params
                  "accountKey" account-key
                  "beforeRefId" before-ref-id))))

(defn corp-wallet-transactions
  ([params account-key]
     (exec-call "/corp/WalletTransactions.xml.aspx"
                (assoc params "accountKey" account-key)))
  ([params account-key before-trans-id]
     (exec-call "/corp/WalletTransactions.xml.aspx"
                (assoc params "beforeTransId" before-trans-id))))


(derive java.util.Map ::params)
(derive java.lang.String ::arg)
(derive java.lang.Integer ::arg)
(derive java.lang.Long ::arg)
(defmulti corp-sheet class)
(defmethod corp-sheet ::params [params]
  (exec-call "/corp/CorporationSheet.xml.aspx" params))
(defmethod corp-sheet ::arg [corp-id]
  (exec-call "/corp/CorporationSheet.xml.aspx" {"corporationId" corp-id}))

(defn corp-starbase-detail
  [params item-id]
  (exec-call "/char/StarbaseDetail.xml.aspx"
             (assoc params "itemId" item-id)))


;;;;;;;;;;;;;;; Alternate Implementation ;;;;;;;;;;;;;;;;;;

(def key-types #{:none :limited :full})
(def cache-styles #{:short :long :modified-short})

(defprotocol ApiCall
  "Defines the protocol for calling the Eve API."
  (api-call [x params] "Call this API service with the supplied parameters."))

(defn sort-params [params]
  (into (sorted-map) params))

(defrecord ApiCallDefinition
  [category
   name
   uri
   key-type
   cache-style
   required-params
   optional-params
   doc]
  ApiCall
  (api-call
   [x params]
    (exec-call uri (sort-params params))))

(def account-params (sorted-set "userID" "apiKey"))
(def character-params (conj account-params "characterID"))

(def
 call-definitions
 (let [defs
       [
	(ApiCallDefinition.
	 "Account"
	 "Characters"
	 "/account/Characters.xml.aspx"
	 :limited
	 :short
	 account-params
	 nil
	 "Returns a list of all characters on an account.")
  
	(ApiCallDefinition.
	 "Account"
	 "Account Status"
	 "/account/AccountStatus.xml.aspx"
	 :full
	 :short
	 account-params
	 nil
	 "Returns basic account information including when the subscription lapses, total play time in minutes, total times logged on and date of account creation. In the case of game time code accounts it will also look for available offers of time codes. ")

	(ApiCallDefinition.
	 "Character"
	 "Market Orders"
	 "/char/MarketOrders.xml.aspx"
	 :full
	 :long
	 character-params
	 nil
	 "Returns a list of market orders for your character.")

	;; An example with optional params.
	(ApiCallDefinition.
	 "Character"
	 "Wallet Journal"
	 "/char/WalletJournal.xml.aspx"
	 :full
	 :modified-short
	 character-params
	 (sorted-set "fromId" "rowCount")
	 "Returns a list of journal transactions for character.")

  
	]]
   (into {} (for [d defs] [(:name d) d]))))

