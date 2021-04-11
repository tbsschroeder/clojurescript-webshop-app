(ns clojurescript.views
  (:require [clojurescript.blocks :as blocks]))

;(defn shop [_]
;  (blocks/base-template)
;  [:h1.center "Amazing Web Shop Application"]
;  (blocks/button->checkout)
;  [:hr {:style "margin: 2rem 0"}]
;  [:div.row {:style "margin: 0; padding-left: 1em;"}
;   (vec (conj (blocks/article->big-cards) :tbody))]
;  (blocks/text->pizza))
;
;(defn checkout [_]
;  ;(blocks/base-template)
;  [:h1.center "Checkout"]
;  (blocks/checkout-table)
;  (blocks/button->buy-more)
;  (blocks/text->pizza))