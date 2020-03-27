(defproject medals-widget "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597"]
                 [reagent "0.10.0"]
                 [cljs-ajax "0.8.0"]

                 [figwheel-sidecar "0.5.19"]

                 [ring "1.8.0"]
                 [ring/ring-defaults "0.3.2"]
                 [compojure "1.6.1"]
                 [org.clojure/test.check "0.9.0"]]
  :plugins [[lein-figwheel "0.5.19"]
            [lein-cljsbuild "1.1.7"]]
  :source-paths ["src/cljs" "src/clj"]
  :target-path "target/%s"
  :resource-paths ["resources" "target/cljsbuild"]
  :clean-targets ^{:protect false} ["resources/public/js" "target"]

  :cljsbuild
  {:builds
   {:app-target
         {:source-paths ["src/cljs"]
          :figwheel     true
          :compiler     {:output-dir    "resources/public/js/out"
                         :asset-path    "/js/out"
                         :source-map    true
                         :optimizations :none
                         :pretty-print  true

                         :modules       {:widget {:entries   #{reuters.medals-widget.widget}
                                                  :output-to "resources/public/js/out/widget.js"}}}}
    :min {:source-paths ["src/cljs"]
          :compiler     {:output-dir    "resources/public/js/min"
                         :asset-path    "/js/min"
                         :optimizations :simple
                         :modules       {:widget {:entries   #{reuters.medals-widget.widget}
                                                  :output-to "resources/public/js/min/widget.js"}}}}}}

  :profiles {:uberjar {:aot        [reuters.dev-svr.main]
                       :main       reuters.dev-svr.main
                       :prep-tasks ["compile" ["cljsbuild" "once" "min"]]}})
