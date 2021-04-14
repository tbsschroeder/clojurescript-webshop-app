(ns cljs-webshop.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [cljs-webshop.blocks :as blocks]
            [cljs-webshop.ajax :as ajax]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (r/atom {:error    {:status 0 :text ""}
                            :warning  ""
                            :articles []
                            :debug    false
                            :page     "shop"}))

(defn body []
  [:div
   (blocks/error-p app-state)
   (blocks/warning-p app-state)
   (blocks/shop app-state)])

;; MAIN
(rdom/render [body]
             (. js/document (getElementById "app")))

(ajax/get-all-articles app-state)


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
