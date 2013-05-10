(ns clojureve.zeromq
  (:import (org.zeromq ZMQ)
           (java.util.zip Inflater)))

(defn make-context [io-threads]
  (ZMQ/context io-threads))

(defn make-socket [context socket-type]
  (.socket context socket-type))

(defn connect [socket address]
  (.connect socket address))

(defn decompress [msg]
  (let [decompressed-msg (byte-array (* 16 (count msg)))
        inflater (doto (Inflater.) (.setInput msg))
        decompressed-length (.inflate inflater decompressed-msg)
        output (byte-array decompressed-length)]
    (.end inflater)
    (System/arraycopy decompressed-msg 0 output 0 decompressed-length)
    (String. output "UTF-8")))

(defn subscribe [handler]
  (let [ctx (make-context 1)
        sub (make-socket ctx ZMQ/SUB)]
    (connect sub "tcp://relay-us-east-1.eve-emdr.com:8050")
    (.subscribe sub (byte-array 0))
    (loop [msg (.recv sub 0)]
      (handler (decompress msg))
      (recur (.recv sub 0)))))

(defn example []
  (subscribe #(println (count %))))


