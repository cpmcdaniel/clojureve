(ns clojureve.camel
  (:import [org.apache.camel.builder RouteBuilder])
  (:import [org.apache.camel.impl DefaultCamelContext DefaultProducerTemplate])
  (:gen-class))

(defmacro defroute [context & body]
  `(.addRoutes
    ~context
    (proxy [RouteBuilder] []
      (configure [] (.. ~'this ~@body)))))

(defn -main [& args]
  (let [context (DefaultCamelContext.)
        userId "userId"
        apiKey "apiKey"]
    (defroute context
      (from "direct:a")
      (to (str "http://api.eve-online.com/account/Characters.xml.aspx?userId="
               userId "&apiKey=" apiKey))
      (to "file:data"))
    (.start context)
    (let [template (DefaultProducerTemplate. context)]
      (doto template
	(.start)
	(.sendBody "direct:a" "")
	(.stop)))
    (. Thread sleep 5000)
    (.stop context)))


