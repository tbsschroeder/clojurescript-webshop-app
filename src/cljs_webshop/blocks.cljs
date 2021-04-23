(ns cljs-webshop.blocks
  (:require [clojure.string :refer [blank?]]
            [cljs-webshop.ajax :as ajax]))

(defn- basket [app-state] (filter #(> (:count %) 0) (:articles @app-state)))

(defn- get-article [app-state id]
  (first (filter #(= (:id %) id) (:articles @app-state))))

(defn- article->btn-increase [app-state id]
  [:button.btn.btn-success.amount-btn
   {:type     "button"
    :on-click #(ajax/manipulate-article app-state id :inc-article)}
   "+"])

(defn- article->btn-decrease [app-state title id]
  [:button.btn.btn-danger.amount-btn
   {:type     "button"
    :on-click #(if (pos-int? (:count (get-article app-state id)))
                 (ajax/manipulate-article app-state id :dec-article)
                 (swap! app-state assoc :warning (str "Cannot have a negative amount of article '" title "'")))}
   "-"])

(defn- article->btn-remove [app-state id]
  [:button.btn.btn-warning
   {:type     "button"
    :on-click #(ajax/manipulate-article app-state id :rem-article)}
   "✘"])

(defn- article->amount-row [app-state article]
  [:div.form-group.count-input
   [:div.input-group
    [:div.input-group-prepend
     (article->btn-decrease app-state (:title article) (:id article))]
    [:input.form-control.input-number {:value    (:count article)
                                       :width    "3em"
                                       :disabled true}]
    [:div.input-group-append
     (article->btn-increase app-state (:id article))]]])

(defn article->big-cards [app-state]
  [:div.articles
   (if (empty? (:articles @app-state))
     [:div.center [:div.lds-ring [:div] [:div] [:div] [:div]]])
   [:div.row {:id "article-row"}
    (for [article (sort-by :title (:articles @app-state))]
      ^{:key (str "article-" (:id article))}
      [:div.card.mb-3
       [:h3.card-header (:title article)]
       [:img.big {:src (:image article) :alt "article image"}]
       [:div.card-body
        [:p.card-text (:description article)]
        (article->amount-row app-state article)]
       [:div.card-footer
        [:small.text-muted (str "Category: " (:category article))]]])]
   ])

(defn article->checkout [app-state]
  [:tbody.basket
   (for [article (basket app-state)]
     ^{:key (str "basket-" (:id article))}
     [:tr
      [:td (:category article)]
      [:td (:title article)]
      [:td [:img.co-img {:src (:image article)}]]
      [:td [:strong {:class "text-warning"} (:count article)]]
      [:td (article->btn-remove app-state (:id article))]])])

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

(defn checkout->table [app-state]
  (if (zero? (count (basket app-state)))
    [:div
     [:h5.text-warning.center "Empty shopping cart! Manager cat is not amused!"]
     (shopping-cat)]
    [:table.table.table-striped.table-hover {:font-size ""}
     [:thead
      [:tr
       [:th "Category"]
       [:th "Article"]
       [:th "Image"]
       [:th "Count"]
       [:th "Remove"]]]
     (article->checkout app-state)
     ]))

(defn button->checkout [app-state]
  [:a.btn.btn-success.co-btn
   {:id       "checkout-button"
    :type     "button"
    :on-click #(swap! app-state assoc :page "checkout")}
   [:img.co-btn-img {:src "img/checkout.svg"
                     :alt "cart"}]
   (str "Checkout (" (count (basket app-state)) ")")])

(defn button->shop [app-state]
  [:a.btn.btn-success.co-btn
   {:id       "shop-button"
    :type     "button"
    :on-click #(swap! app-state assoc :page "shop")}
   [:img.co-btn-img {:src "img/shop.svg"
                     :alt "cart"}]
   "Shop"])


(defn error-p [app-state]
  (let [status (get-in @app-state [:error :status])
        text (get-in @app-state [:error :text])]
    [:div.center.floater
     (when (pos? status)

       [:div.alert.alert-danger.alert-dismissible.fade.show {:role "alert"}
        [:strong "Error"] text
        [:button.close {:type         "button"
                        :data-dismiss "alert"
                        :aria-label   "Close"
                        :on-click     #(swap! app-state assoc :error {:status 0 :text ""})}
         [:span {:aria-hidden "true"} "×"]]])]))

(defn warning-p [app-state]
  (let [text (:warning @app-state)]
    [:div.center.floater
     (when (not-empty text)
       [:div.alert.alert-warning.alert-dismissible.fade.show {:role "alert"}
        [:strong "Warning"] text
        [:button.close {:type         "button"
                        :data-dismiss "alert"
                        :aria-label   "Close"
                        :on-click     #(swap! app-state assoc :warning "")
                        }
         [:span {:aria-hidden "true"} "×"]]])]))

(defn shop [app-state]
  [:div
   (button->checkout app-state)
   [:hr {:style {:margin "2rem 0" :background-color "white"}}]
   (article->big-cards app-state)
   [:hr {:style {:margin "2rem 0" :background-color "white"}}]])

(defn checkout [app-state]
  [:div
   (button->shop app-state)
   (checkout->table app-state)])