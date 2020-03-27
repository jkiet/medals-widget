(ns reuters.dev-svr.main
  (:gen-class))

(defn -main
  [& args]
  (require 'reuters.dev-svr.core)
  (apply (ns-resolve 'reuters.dev-svr.core 'start!) args))