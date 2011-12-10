(ns clojureve.core
  (:gen-class)
  (:use compojure.core
        [hiccup.middleware :only (wrap-base-url)]
        [ring.util.response :only (resource-response)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

(defn -main [& args]
  (println "Welcome to clojureve! These are your args:" args))

(defroutes main-routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (POST "/login" [username password]
        {:status 200
         :headers {"Content-Type" "text/html"}
         :session {"Blah" "Foo"}
         :body (str "<h1>Posted username: " username ", password: "
                    (apply str (repeat (count password) "*")) "</h1>")})
  (GET "/user/:id" [id]
       (str "<h1>Hello, " id "!</h1>"))
  (GET "/session" {{blah "Blah"} :session}
       (str "<h1>" blah "</h1>"))
  ;(route/resources "/")
  (route/not-found "Resource not found"))

(def app
  (->
   (handler/site main-routes)
   (wrap-base-url)))
