(ns cljs-webshop.ui.detail
  (:require [re-frame.core :as rf]
            [cljs-webshop.ui.lib :as lib]))

(defn render-article [id]
  [:div
   (let [articles @(rf/subscribe [:articles])
         article (first (filter #(= (:id %) id) articles))]
     [:div.row.article
      [:div.col-4 [:img.detail {:src (:image article) :alt "article image"}]]
      [:div.col-8
       [:h4 {:title article}]
       [:p.card-text (:description article)]
       [:div.row.justify-content-md-center
        [:div.col-md-auto (lib/article->amount-row article)]]]]
     )])

(defn detail []
  [:div.container
   (lib/navbar->shop lib/button->checkout)
   (let [id @(rf/subscribe [:detail])]
     (render-article id))
   [:hr {:style {:margin "2rem 0" :background-color "#555"}}]])
