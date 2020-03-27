(ns reuters.medals-widget.widget
  (:require [goog.dom :as dom]
            [reagent.core :as r]
            [reagent.dom :as rd]
            [ajax.core :as ajax]
            [clojure.string :as str]))


#_(def data-url "https://s3-us-west-2.amazonaws.com/reuters.medals-widget/medals.json")
(def data-url "/gen/medals.json")

(def state (r/atom {:data  nil
                    :error nil
                    :sort  nil}))

(defn view
  []
  [:div.medal-count
   [:h1 "MEDAL COUNT"]
   (when-some [{:keys [status
                       status-text]} (:error @state)]
     [:div.error (str "Unable to retrieve data ("
                      status
                      ","
                      status-text
                      ")") ])
   (when-some [data (:data @state)]
     (->> (map (fn [{:keys [gold
                            silver
                            bronze]
                     :as   m}]
                   (assoc m :total (+ gold silver bronze))) data)
          (sort-by
            (case (:sort @state)
              :gold (juxt :gold :silver)
              :silver (juxt :silver :gold)
              :bronze (juxt :bronze :gold)
              :total (juxt :total :gold)))
          reverse
          (take 10)
          vec
          (reduce-kv
            (fn [coll k {:keys [code
                                gold
                                silver
                                bronze
                                total]}]
                (conj coll (with-meta
                             [:li
                              [:span.idx (inc k)]
                              [:span.flag {:class code}]
                              [:span.code code]
                              [:span.gold gold]
                              [:span.silver silver]
                              [:span.bronze bronze]
                              [:span.total total]]
                             {:key code})))
            [])
          (into [:ul (with-meta
                       [:li.hdr
                        [:span.spacer]
                        [:span.gold {:class    ({:gold "active"} (:sort @state))
                                     :on-click #(swap! state assoc :sort :gold)}
                         [:span.circle]]
                        [:span.silver {:class    ({:silver "active"} (:sort @state))
                                       :on-click #(swap! state assoc :sort :silver)}
                         [:span.circle]]
                        [:span.bronze {:class    ({:bronze "active"} (:sort @state))
                                       :on-click #(swap! state assoc :sort :bronze)}
                         [:span.circle]]
                        [:span.total {:class    ({:total "active"} (:sort @state))
                                      :on-click #(swap! state assoc :sort :total)}
                         "TOTAL"]]
                       {:key "hdr"})])))])

(defn init!
  []
  (let [params (reduce
                 (fn [m p]
                     (let [[k v] (str/split p #"=")]
                       (assoc m k v)))
                 {}
                 (-> js/window
                     .-location
                     .-href
                     (str/split #"\?")
                     last
                     (str/split #"\&")))]

    (swap! state assoc :sort (keyword
                               (or
                                 (#{"total" "gold" "silver" "bronze"} (get params "sort"))
                                 "gold")))
    (rd/render [view] (dom/getElement (get params "element_id")))
    (ajax/GET
      data-url
      {:handler         (fn [d] (swap! state assoc :data d))
       :error-handler   (fn [r] (swap! state assoc :error r))
       :response-format :json
       :keywords?       true})))


(enable-console-print!)
(init!)
