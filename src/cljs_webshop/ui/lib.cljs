( ns cljs-webshop.ui.lib
  (:require [re-frame.core :as rf]))

(defn button->checkout []
  [:a.btn.btn-warning.co-btn
   {:id       "checkout-button"
    :type     "button"
    :on-click #(rf/dispatch [:change-page "checkout"])}
   [:img.co-btn-img {:src "img/checkout.svg"
                     :alt "cart"}]
   [:span  "Checkout "]
   [:span.badge.bg-primary @(rf/subscribe [:basket-count])]
   ""])

(defn button->shop []
  [:a.btn.btn-light.co-btn
   {:id       "shop-button"
    :type     "button"
    :on-click #(rf/dispatch [:change-page "shop"])}
   [:img.co-btn-img {:src "img/shop.svg"
                     :alt "cart"}]
   "Shop"])

(defn navbar->shop [button]
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
     (button)
     ]]])

(defn- article->btn-increase [id]
  [:button.btn.btn-success.amount-btn
   {:type     "button"
    :on-click #(rf/dispatch [:increase-article id])}
   "+"])

(defn- article->btn-decrease [title id]
  [:button.btn.btn-danger.amount-btn
   {:type     "button"
    :on-click #(if (pos-int? (:count @(rf/subscribe [:get-article id])))
                 (rf/dispatch [:decrease-article id])
                 (rf/dispatch [:display-warning (str "Cannot have a negative amount of article '" title "'")]))}
   "-"])

(defn article->amount-row [article]
  [:div.form-group.count-input
   [:div.input-group
    [:div.input-group-prepend
     (article->btn-decrease (:title article) (:id article))]
    [:input.form-control.input-number {:value    (:count article)
                                       :width    "3em"
                                       :disabled true}]
    [:div.input-group-append
     (article->btn-increase (:id article))]]])