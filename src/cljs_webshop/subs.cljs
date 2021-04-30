(ns cljs-webshop.subs
  (:require [re-frame.core :as rf]))

;; Because subscriptions return a ratom, they must always be dereferenced to obtain the value.

(rf/reg-sub
  :articles
  (fn [db _]
    (:articles db)))

(rf/reg-sub
  :error
  (fn [db _]
    (:error db)))

(rf/reg-sub
  :warning
  (fn [db _]
    (:warning db)))

(rf/reg-sub
  :page
  (fn [db _]
    (:page db)))

(rf/reg-sub
  :basket
  (fn [db _]
    (filter #(> (:count %) 0)
            (:articles db))))

(rf/reg-sub
  :basket-count
  (fn [db _]
    (count (filter #(> (:count %) 0)
                   (:articles db)))))

(rf/reg-sub
  :get-article
  (fn [db [_ id]]
    (first (filter #(= (:id %) id)
                   (:articles db)))))
