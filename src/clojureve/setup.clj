(ns clojureve.setup
  (:use [clojure.contrib.sql :as sql :only ()]))

(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :subname "//localhost:3306/eve"
         :user "user"
         :password "password"})

(defn drop-table
  "Drop users table"
  [table-name]
  (try
    (sql/drop-table table-name)
    (catch Exception _)))

(defn create-users
  "Create the users table"
  []
  (sql/create-table
   :ce_users
   [:id "integer" "PRIMARY KEY"]
   [:username "varchar(24)" "NOT NULL"]
   [:password "varchar(24)" "NOT NULL"]))

(defn create-accounts
  "Create the accounts table"
  []
  (sql/create-table
   :ce_accounts
   [:id "integer" "PRIMARY KEY"]
   [:user_id "integer" "NOT NULL"]
   [:name "varchar(64)" "NOT NULL"]
   [:keyid "integer" "NOT NULL"]
   [:vcode "char(64)"]
   ["INDEX" :user_idx "(user_id)"]
   ["FOREIGN KEY" "(user_id)" "REFERENCES" "ce_users(id)"]))

(defn insert-users
  "Insert initial user records"
  []
  (sql/insert-rows
   :ce_users
   [1 "craig" "eve"]))

(defn insert-accounts
  "Insert initial eve accounts"
  []
  (sql/insert-rows
   :ce_accounts
   [1 1 "cpmcda01" "281377"
    "B5fggev9UwutRu2QmSxBNa5QdngNcHZNSq6FRkV0AYcZKYcqrIOn5fFnkn0jDsNm"]
   [2 1 "cpmcda02" "281381"
    "T4aH0Isz6SzfGdqdrWP16gDXHsK7yrrlyIUfioREjHWaS0GJsP2oN2BOntAsszOc"]))

(defn init-db
  "Create tables and write initial data to the database."
  []
  (sql/with-connection db
    (sql/transaction
     (drop-table :ce_accounts)
     (drop-table :ce_users)
     (create-users)
     (create-accounts)
     (insert-users)
     (insert-accounts))))
