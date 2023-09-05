(ns cljs-webshop.core
  (:require ["react-dom/client" :refer [createRoot]]
            [cljs-webshop.events]
            [cljs-webshop.ui.checkout :as c]
            [cljs-webshop.ui.detail :as d]
            [cljs-webshop.ui.shop :as s]
            [cljs-webshop.ui.floater :as f]
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
   (f/error-p)
   (f/warning-p)
   (let [container @(rf/subscribe [:container])]
     (cond
       (= "shop" container) (s/shop)
       (= "checkout" container) (c/checkout)
       (= "detail" container) (d/detail)
       :else (rf/dispatch [:display-error "404" (str "internal routing error for '" container "'")])))])

;; -- Entry Point -------------------------------------------------------------

(defonce root (createRoot (gdom/getElement "app")))

(defn render
  []
  (.render root (r/as-element [body])))

(defn ^:dev/after-load clear-cache-and-render!
  []
  (rf/clear-subscription-cache!)
  (render)
  (rf/dispatch [:get-all-articles]))

(defn init
  "Entrypoint into the application."
  []
  (rf/dispatch-sync [:initialize-db])
  (rf/dispatch-sync [:get-all-articles])
  (render))
