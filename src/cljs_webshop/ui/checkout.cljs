(ns cljs-webshop.ui.checkout
  (:require [re-frame.core :as rf]
            [cljs-webshop.ui.lib :as lib]))

(defn- article->btn-remove [id]
  [:button.btn.btn-warning
   {:type     "button"
    :on-click #(rf/dispatch [:remove-article id])}
   "✘"])

(defn article->checkout []
  [:tbody.basket
   (for [article @(rf/subscribe [:basket])]
     ^{:key (str "basket-" (:id article))}
     [:tr
      [:td (:category article)]
      [:td (:title article)]
      [:td [:img.co-img {:src (:image article)}]]
      [:td [:strong {:class "text-warning"} (:count article)]]
      [:td (article->btn-remove (:id article))]])])

(defn- shopping-cat []
  [:div.cat
   [:div.head
    [:div.face
     [:div.stripes [:div.top] [:div.left] [:div.right]]
     [:div.eyes [:div.left] [:div.right]]
     [:div.nose]
     [:div.mouth]]
    [:div.ears [:div.left] [:div.right]]]
   [:div.suit
    [:div.collar [:div.top]]
    [:div.arms [:div.left] [:div.right]]
    [:div.paws [:div.left] [:div.right]]
    [:div.body]]
   [:div.tail]
   [:div.shadow]])

(defn checkout->table []
  (if (zero? @(rf/subscribe [:basket-count]))
    [:div
     [:br]
     [:br]
     [:h5.text-info.center "You have en empty shopping cart!"]
     [:h5.text-info.center "My manager is not amused!"]
     [:br]
     [:br]
     (shopping-cat)]
    [:div.container
     [:table.table.table-striped.table-hover {:font-size ""}
      [:thead
       [:tr
        [:th "Category"]
        [:th "Article"]
        [:th "Image"]
        [:th "Count"]
        [:th "Remove"]]]
      (article->checkout)]]))

(defn checkout []
  [:div.container
   (lib/navbar->shop lib/button->shop)
   (checkout->table)])