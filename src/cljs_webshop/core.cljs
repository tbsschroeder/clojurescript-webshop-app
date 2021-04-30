(ns cljs-webshop.core
  (:require [cljs-webshop.events]
            [cljs-webshop.views :as views]
            [cljs-webshop.events]                           ;; These two are only required to make the compiler
            [cljs-webshop.subs]                             ;; load them (see re-frame docs/App-Structure.md)
            [re-frame.core :as rf]
            [reagent.dom]
            [reagent.core :as reagent]))

(enable-console-print!)                                     ;; so that println writes to `console.log`

;; -- UI

(defn body []
  [:div
   (views/error-p)
   (views/warning-p)
   (let [page @(rf/subscribe [:page])]
     (cond
       (= "shop" page) (views/shop)
       (= "checkout" page) (views/checkout)
       :else (rf/dispatch [:display-error "404" (str "internal routing error for '" page "'")])))])

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
  (rf/dispatch [:get-all-articles]))

(defn init
  "Entrypoint into the application."
  []
  (rf/dispatch-sync [:initialize-db])
  (rf/dispatch-sync [:get-all-articles])
  (render))
