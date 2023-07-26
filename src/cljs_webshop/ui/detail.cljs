(ns cljs-webshop.ui.detail
  (:require [re-frame.core :as rf]
            [cljs-webshop.ui.lib :as lib]))

(defn navbar->checkout []
  [:nav.navbar.navbar-expand-lg.bg-primary {:data-bs-theme "dark"}
   [:div.container-fluid
    [:a.navbar-brand {:href "#"} "Amazing Web Shop Application"]
    [:button.navbar-toggler {:type "button"
                             :data-bs-toggle "collapse"
                             :data-bs-target "#navbarColor01"
                             :aria-controls "navbarColor01"
                             :aria-expanded "false"
                             :aria-label "Toggle navigation"}
     [:span.navbar-toggler-icon]]
    [:div#navbarColor01.collapse.navbar-collapse
     [:ul.navbar-nav.me-auto]
     (lib/button->shop)
     (lib/button->checkout)
     ]]])

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
   (navbar->checkout)
   (let [id @(rf/subscribe [:detail])]
     (render-article id))
   [:hr {:style {:margin "2rem 0" :background-color "#555"}}]])