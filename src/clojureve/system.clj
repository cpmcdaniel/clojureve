(ns clojureve.system)


(defprotocol Lifecycle
  "Lifecycle start/stop abstraction."
  (start [ctx]
    "Starts services for the given context and returns the new context.")
  (stop [ctx]
    "Stops services for the given context and returns the new context."))

;; Main entry point for your system. Customize as needed.
;; Optionally create additional types for sub-modules and call their lifecycle
;; methods from the MainSystem methods.
(defrecord MainSystem
    ;; Add more fields as needed...
    [status]
  
  Lifecycle

  (start [system] ;; Customize the startup...
    (assoc system :status :started))

  (stop [system] ;; And the shutdown...
    (assoc system :status :stopped)))


(defn system
  "Constructs the default system."
  []
  (MainSystem. :initialized))
