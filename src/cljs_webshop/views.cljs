(ns cljs-webshop.views
  (:require [clojure.string :refer [blank?]]
            [re-frame.core :as rf]))

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

(defn- article->btn-remove [id]
  [:button.btn.btn-warning
   {:type     "button"
    :on-click #(rf/dispatch [:remove-article id])}
   "✘"])

(defn- article->amount-row [article]
  [:div.form-group.count-input
   [:div.input-group
    [:div.input-group-prepend
     (article->btn-decrease (:title article) (:id article))]
    [:input.form-control.input-number {:value    (:count article)
                                       :width    "3em"
                                       :disabled true}]
    [:div.input-group-append
     (article->btn-increase (:id article))]]])

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
           [:h3.card-header (:title article)]
           [:img.big {:src (:image article) :alt "article image"}]
           [:div.card-body
            [:p.card-text (:description article)]
            (article->amount-row article)]
           [:div.card-footer
            [:small.text-muted (str "Category: " (:category article))]]])]))])

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
     [:h5.text-info.center "Empty shopping cart! Manager cat is not amused!"]
     (shopping-cat)]
    [:table.table.table-striped.table-hover {:font-size ""}
     [:thead
      [:tr
       [:th "Category"]
       [:th "Article"]
       [:th "Image"]
       [:th "Count"]
       [:th "Remove"]]]
     (article->checkout)
     ]))

(defn button->checkout []
  [:a.btn.btn-success.co-btn
   {:id       "checkout-button"
    :type     "button"
    :on-click #(rf/dispatch [:change-page "checkout"])
    }
   [:img.co-btn-img {:src "img/checkout.svg"
                     :alt "cart"}]
   (str "Checkout (" @(rf/subscribe [:basket-count]) ")")
   ""
   ])

(defn button->shop []
  [:a.btn.btn-success.co-btn
   {:id       "shop-button"
    :type     "button"
    :on-click #(rf/dispatch [:change-page "shop"])}
   [:img.co-btn-img {:src "img/shop.svg"
                     :alt "cart"}]
   "Shop"])

(defn error-p []
  (let [status (:status @(rf/subscribe [:error]))
        text (:text @(rf/subscribe [:error]))]
    [:div.center.floater
     (when (pos? status)

       [:div.alert.alert-danger.alert-dismissible.fade.show {:role "alert"}
        [:strong "Error"] text
        [:button.close {:type         "button"
                        :data-dismiss "alert"
                        :aria-label   "Close"
                        :on-click     #(rf/dispatch [:clear-error])}
         [:span {:aria-hidden "true"} "×"]]])]))

(defn warning-p []
  (let [text @(rf/subscribe [:warning])]
    [:div.center.floater
     (when (not-empty text)
       [:div.alert.alert-info.alert-dismissible.fade.show {:role "alert"}
        [:strong "Warning"] text
        [:button.close {:type         "button"
                        :data-dismiss "alert"
                        :aria-label   "Close"
                        :on-click     #(rf/dispatch [:clear-warning])
                        }
         [:span {:aria-hidden "true"} "×"]]])]))

(defn shop []
  [:div
   (button->checkout)
   [:hr {:style {:margin "2rem 0" :background-color "#555"}}]
   (article->big-cards)
   [:hr {:style {:margin "2rem 0" :background-color "#555"}}]])

(defn checkout []
  [:div
   (button->shop)
   (checkout->table)])