(ns clojureve.test.test-util)

(defn load-api-key
  "For this to work, you will need to create a api.edn file in the project test directory. The file should contain a single map: {:keyID \"xxxxx\" :vCode \"yyyyy\"} This key should have FULL api access."
  []
  (read-string (slurp "test/api.edn")))
