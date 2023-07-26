(ns cljs-webshop.ui.shop
  (:require [re-frame.core :as rf]
            [cljs-webshop.ui.lib :as lib]))

(defn form->search []
  [:form.d-flex
   [:input.form-control.me-sm-2 {:type "search"
                                 :placeholder "Search"}]
   [:button.btn.btn-secondary.my-2.my-sm-0 {:type "submit"} "Search"]])

(defn navbar->shop []
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
     (form->search)
     (lib/button->checkout)
     ]]])

(defn- loader []
  [:div.center
   [:div.lds-ring [:div] [:div] [:div] [:div]]
   [:p "Sexy articles will appear soon!"]])

(defn article->big-cards []
  [:div.articles
   (let [articles @(rf/subscribe [:articles])]
     (if (empty? articles)
       (loader)
       [:div.row {:id "article-row"}
        (for [article (sort-by :title articles)]
          ^{:key (str "article-" (:id article))}
          [:div.card.mb-3
           [:h4.card-header (:title article)]
           [:img.big.d-block {:src (:image article) :alt "article image"}]
           [:div.card-body
            [:p.card-text (:description article)]
            (lib/article->amount-row article)]
           [:div.card-body
            [:a {:href     "#"
                 :on-click #(rf/dispatch [:change-page "detail" (:id article)])} "Details"]]
           [:div.card-footer
            [:small.text-muted (str "Category: " (:category article))]]])]))])

(defn shop []
  [:div.container
   (navbar->shop)
   (article->big-cards)
   [:hr {:style {:margin "2rem 0" :background-color "#555"}}]])