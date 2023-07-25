(ns cljs-webshop.core
  (:require ["react-dom/client" :refer [createRoot]]
            [cljs-webshop.events]
            [cljs-webshop.views :as views]
            [cljs-webshop.events]
            [cljs-webshop.subs]
            [goog.dom :as gdom]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [reagent.dom]))

(enable-console-print!)

;; -- UI -------------------------------------------------------------

(defn body []
  [:div
   (views/error-p)
   (views/warning-p)
   (let [container @(rf/subscribe [:container])]
     (cond
       (= "shop" container) (views/shop)
       (= "checkout" container) (views/checkout)
       :else (rf/dispatch [:display-error "404" (str "internal routing error for '" container "'")])))])

;; -- Entry Point -------------------------------------------------------------

(defonce root (createRoot (gdom/getElement "app")))

(defn render
  []
  (.render root (r/as-element [body])))

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
