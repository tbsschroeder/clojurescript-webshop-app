(ns clojurescript.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [clojurescript.blocks :as blocks]
            [clojurescript.ajax :as ajax]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(def api "http://localhost:8080/api")

(defonce app-state (r/atom {:error    {:status 0 :text ""}
                            :warning  ""
                            :articles []
                            :debug    false
                            :page     "shop"
                            :urls     {:all-articles (str api "/article/all")
                                       :inc-article  (str api "/article/inc")
                                       :dec-article  (str api "/article/dec")
                                       :rem-article  (str api "/article/rem")}}))

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
