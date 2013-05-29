(ns clojureve.api
  (:require [clj-http.client :as http]
            [clojure.string :refer [join]]))

(def ^:dynamic *api-server* "https://api.eveonline.com")

(defrecord ApiKey [keyID vCode])

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

(def wallet-journal
  (partial exec-call "/char/WalletJournal.xml.aspx"))

(def wallet-transactions
  (partial exec-call "/char/WalletTransactions.xml.aspx"))

(def character-info
  (partial exec-call "/eve/CharacterInfo.xml.aspx"))

(defn factional-war-stats
  ([] (anonymous-call "/eve/FacWarStats.xml.aspx"))
  ([params]
     (exec-call "/char/FacWarStats.xml.aspx" params)))

(def kill-log
  (partial exec-call "/char/KillLog.xml.aspx"))


(def mail-bodies
  (partial exec-call "/char/MailBodies.xml.aspx"))


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
(def corp-wallet-journal
  (partial exec-call "/corp/WalletJournal.xml.aspx"))
(def corp-wallet-transactions
  (partial exec-call "/corp/WalletTransactions.xml.aspx"))
(def corp-sheet
  (partial exec-call "/corp/CorporationSheet.xml.aspx"))
(def corp-starbase-detail
  (partial exec-call "/char/StarbaseDetail.xml.aspx"))


