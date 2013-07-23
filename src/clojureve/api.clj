(ns clojureve.api
  (:require [clj-http.client :as http]
            [clojure.string :refer [join]]))

;; A record to hold the user's API key.
;; Additional post parameters can be added to the instances.
;; All params (including API key fields) will be posted.
(defrecord ApiKey [keyID vCode])

(defn- api-call [path-info]
  "Returns a function (closure) for the give api call. The returned function takes the server and a map of parameters as arguments."
  (fn [server params]
    (http/post (str server path-info)
               {:form-params params
                :as :stream})))

(defn- anonymous-call
  "Wraps api-call to provide an empty param map."
  [path-info]
  (let [call (api-call path-info)]
    (fn [server]
      (call server {}))))

;; Anonymous calls. 
(def #^{:category "Server"
        :doc "Gets the status of the EVE server."}
  server-status
  (anonymous-call "/server/ServerStatus.xml.aspx"))

(def #^{:category "Eve"
        :doc "Returns a list of alliances in EVE."}
  alliance-list
  (anonymous-call "/eve/AllianceList.xml.aspx"))

(def #^{:category "Eve"
        :doc "Returns a tree of the certificates in EVE."}
  certificate-tree
  (anonymous-call "/eve/CertificateTree.xml.aspx"))

(def conquerable-station-list
  (anonymous-call "/eve/ConquerableStationList.xml.aspx"))

(def error-list
  (anonymous-call "/eve/ErrorList.xml.aspx"))
(def faction-war-top-stats
  (anonymous-call "/eve/FacWarTopStats.xml.aspx"))
(def ref-types
  (anonymous-call "/eve/RefTypes.xml.aspx"))
(def skill-tree
  (anonymous-call "/eve/SkillTree.xml.aspx"))
(def faction-war-systems
  (anonymous-call "/map/FacWarSystems.xml.aspx"))
(def jumps
  (anonymous-call "/map/Jumps.xml.aspx"))
(def kills
  (anonymous-call "/map/Kills.xml.aspx"))
(def sovereignty
  (anonymous-call "/map/Sovereignty.xml.aspx"))

(defn character-id [server & names]
  ((api-call "/eve/CharacterID.xml.aspx")
             server
             ;; TODO - encode each name.
             {:names (clojure.string/join "," names)}))

(defn character-name [server & ids]
  ((api-call "/eve/CharacterName.xml.aspx")
             server
             {:ids (clojure.string/join "," ids)}))

;; Account calls.
(def character-list
  (api-call "/account/Characters.xml.aspx"))
(def account-status
  (api-call "/account/AccountStatus.xml.aspx"))

;; Character calls.
(def account-balance
  (api-call "/char/AccountBalance.xml.aspx"))
(def asset-list
  (api-call "/char/AssetList.xml.aspx"))
(def calendar-event-attendees
  (api-call "/car/CalendarEventAttendees.xml.aspx"))
(def character-sheet
  (api-call "/char/CharacterSheet.xml.aspx"))
(def contact-list
  (api-call "/char/ContactList.xml.aspx"))
(def contact-notifications
  (api-call "/char/ContactNotifications.xmlaspx"))
(def industry-jobs
  (api-call "/char/IndustryJobs.xml.aspx"))
(def mailing-lists
  (api-call "/char/MailingLists.xml.aspx"))
(def mail-messages
  (api-call "/char/MailMessages.xml.aspx"))
(def market-orders
  (api-call "/char/MarketOrders.xml.aspx"))
(def medals
  (api-call "/char/Medals.xml.aspx"))
(def notifications
  (api-call "/char/Notifications.xml.aspx"))
(def standings
  (api-call "/char/Standings.xml.aspx"))
(def research
  (api-call "/char/Research.xml.aspx"))
(def skill-in-training
  (api-call "/char/SkillInTraining.xml.aspx"))
(def skill-queue
  (api-call "/char/SkillQueue.xml.aspx"))
(def upcoming-calendar-events
  (api-call "/char/UpcomingCalendarEvents.xml.aspx"))

(def wallet-journal
  (api-call "/char/WalletJournal.xml.aspx"))

(def wallet-transactions
  (api-call "/char/WalletTransactions.xml.aspx"))

(def character-info
  (api-call "/eve/CharacterInfo.xml.aspx"))

(defn factional-war-stats
  ([] (anonymous-call "/eve/FacWarStats.xml.aspx"))
  ([params]
     (api-call "/char/FacWarStats.xml.aspx" params)))

(def kill-log
  (api-call "/char/KillLog.xml.aspx"))


(def mail-bodies
  (api-call "/char/MailBodies.xml.aspx"))


;; Corporation calls.
(def corp-account-balance
  (api-call "/corp/AccountBalance.xml.aspx"))
(def corp-asset-list
  (api-call "/corp/AssetList.xml.aspx"))
(def corp-calendar-event-attendees
  (api-call "/corp/CalendarEventAtendees.xml.aspx"))
(def corp-contact-list
  (api-call "/corp/ContactList.xml.aspx"))
(def corp-container-log
  (api-call "/corp/ContainerLog.xml.aspx"))
(def corp-faction-war-stats
  (api-call "/corp/FacWarStats.xml.aspx"))
(def corp-industry-jobs
  (api-call "/corp/IndustryJobs.xml.aspx"))
(def corp-kill-log
  (api-call "/corp/KillLog.xml.aspx"))
(def corp-market-orders
  (api-call "/corp/MarketOrders.xml.aspx"))
(def corp-medals
  (api-call "/corp/Medals.xml.aspx"))
(def corp-member-medals
  (api-call "/corp/MemberMedals.xml.aspx"))
(def corp-member-security
  (api-call "/corp/MemberSecurity.xml.aspx"))
(def corp-member-security-log
  (api-call "/corp/MemberSecurityLog.xml.aspx"))
(def corp-member-tracking
  (api-call "/corp/MemberTracking.xml.aspx"))
(def corp-standings
  (api-call "/corp/Standings.xml.aspx"))
(def corp-outpost-list
  (api-call "/corp/OutpostList.xml.aspx"))
(def corp-outpost-service-detail
  (api-call "/corp/OutpostServiceDetail.xml.aspx"))
(def corp-starbase-list
  (api-call "/corp/StarbaseList.xml.aspx"))
(def corp-shareholders
  (api-call "/corp/Shareholders.xml.aspx"))
(def corp-titles
  (api-call "/corp/Titles.xml.aspx"))
(def corp-wallet-journal
  (api-call "/corp/WalletJournal.xml.aspx"))
(def corp-wallet-transactions
  (api-call "/corp/WalletTransactions.xml.aspx"))
(def corp-sheet
  (api-call "/corp/CorporationSheet.xml.aspx"))
(def corp-starbase-detail
  (api-call "/char/StarbaseDetail.xml.aspx"))


