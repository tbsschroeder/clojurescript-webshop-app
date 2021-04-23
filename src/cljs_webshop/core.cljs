(ns cljs-webshop.core
  (:require [reagent.dom]
            [re-frame.core :as rf]
            [cljs-webshop.blocks :as blocks]
            [cljs-webshop.ajax :as ajax]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:error    {:status 0 :text ""}
                          :warning  ""
                          :articles []
                          :debug    false
                          :page     "shop"}))

;; -- UI

(defn render-status []
  (blocks/error-p app-state)
  (blocks/warning-p app-state))

(defn render-body []
  (let [page (:page @app-state)]
    (cond
      (= "shop" page) (blocks/shop app-state)
      (= "checkout" page) (blocks/checkout app-state)
      :else (swap! app-state assoc :error {:status "404" :text "internal routing error"}))))

(defn body []
  [:div
   (render-status)
   (render-body)])

;; -- Entry Point

(defn render
  []
  (reagent.dom/render [body]
             (js/document.getElementById "app")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code. We force a UI update by clearing
  ;; the Reframe subscription cache.
  (rf/clear-subscription-cache!)
  (render)
  (ajax/get-all-articles app-state))

(defn run
  []
  (rf/dispatch-sync [:initialize])
  (render))
