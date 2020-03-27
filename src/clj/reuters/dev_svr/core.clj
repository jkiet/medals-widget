(ns reuters.dev-svr.core
  (:require [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :as ring-defaults]
            [compojure.core :as compojure]
            [clojure.test.check.generators :as gen]))


(defn start!
  []
  (jetty/run-jetty
    (ring-defaults/wrap-defaults
      (compojure/routes
        (compojure/GET "/" []
          {:status  200
           :headers {"Content-Type" "text/html"}
           :body    (slurp (io/resource "public/index.html"))})
        (compojure/GET "/gen/medals.json" []
          (gen/generate
            (gen/hash-map
              :status (gen/frequency [[5 (gen/return 200)]
                                      [1 (gen/elements [404 500 503])]])
              :headers (gen/return {"Content-Type" "application/json"})
              :body (gen/return (slurp (io/resource "public/data/medals.json"))))))
        (constantly
          {:status  404
           :headers {"Content-Type" "text/plain"}
           :body    "404 Not Found"}))
      {:static {:resources "public"}})
    {:port  8080
     :join? false}))
