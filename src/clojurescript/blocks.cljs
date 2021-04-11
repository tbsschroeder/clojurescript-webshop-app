(ns clojurescript.blocks)

(defn- amount-btn [action id class value]
  [:form.amount-article-form {:action action
                              :method :POST}
   [:input {:name   "id"
            :value  id
            :hidden true}]
   [:input {:class (str "btn " class)
            :type  :submit
            :value value}]])

(defn- amount-input [article]
  [:div.form-group.count-input
   [:div.input-group
    [:div.input-group-prepend
     (amount-btn "/article/dec" (:id article) "btn-danger" "-")]
    [:input.form-control.input-number {:value    (:count article)
                                       :width    "3em"
                                       :disabled "true"}]
    [:div.input-group-append
     (amount-btn "/article/inc" (:id article) "btn-success" "+")]]])

;(defn- 🐱 []
;  [:div.cat
;   [:div.head
;    [:div.face
;     [:div.stripes [:div.top] [:div.left] [:div.right]]
;     [:div.eyes [:div.left] [:div.right]]
;     [:div.nose]
;     [:div.mouth]]
;    [:div.ears [:div.left] [:div.right]]]
;   [:div.suit
;    [:div.collar [:div.top]]
;    [:div.arms [:div.left] [:div.right]]
;    [:div.paws [:div.left] [:div.right]]
;    [:div.body]]
;   [:div.tail]
;   [:div.shadow]])

(defn article->big-cards [articles]
  [:div.articles
   (if (empty? articles)
     [:div.center [:div.lds-ring [:div] [:div] [:div] [:div]]])
   [:div.row {:id "article-row"}
    (for [article (sort-by :title articles)]
      ^{:key (str "article-" (:id article))}
      [:div.card.mb-3
       [:h3.card-header (:title article)]
       [:div.card-body
        [:h6.card-subtitle.text-muted (:category article)]]
       [:img.big {:src (:image article) :alt "article image"}]
       [:div.card-body
        [:p.card-text (:description article)]
        [:a.card-link {:href "#"} "More"]
        (amount-input article)]])]
   ])

;(defn article->small-cards []
;  (for [article (sort-by :title (article/query-all))]
;    [:div.card.text-white.bg-secondary.mb-3
;     [:div.card-header [:h4 (:title article)]]
;     [:div.card-body
;      [:p.card-text (:description article)]
;      [:img.small {:src (:image article)}]
;      (amount-input article)]]))

;(defn article->checkout []
;  (for [article (sort-by (juxt :category :title) (article/query-all-with-count))]
;    [:tr
;     [:td (:category article)]
;     [:td (:title article)]
;     [:td [:img.co-img {:src (:image article)}]]
;     [:td [:strong {:class "text-warning"} (:count article)]]
;     [:td (amount-btn "/article/rem" (:id article) "btn-warning btn-sm" "✘")]]))

;(defn checkout-table []
;  (if (article/has-articles-with-data?)
;    [:table.table.table-striped.table-hover {:font-size ""}
;     [:thead
;      [:tr
;       [:th "Category"]
;       [:th "Article"]
;       [:th "Image"]
;       [:th "Count"]
;       [:th "Remove"]]]
;     (vec (conj (article->checkout) :tbody))]
;    [:div
;     [:h2.text-warning.center "Empty shopping cart! Manager cat is not amused!"]
;     (🐱)]))

(defn button->checkout [articles]
  [:a.btn.btn-success.co-btn
   {:type "button"
    :href "/checkout"}
   (let [article-count (if (empty? articles)
                         0
                         (count (filter #(> (:count %) 0) articles)))]
     [:img.co-btn-img {:src "img/shopping-cart.svg"
                       :alt "cart"}]
     (str "Checkout (" article-count ")"))]
  )

(defn button->buy-more []
  [:a.btn.btn-success.buy-more-btn
   {:type "button"
    :href "/"}
   [:img.buy-more-btn-img {:src "img/shopping-cart.svg"
                           :alt "cart"}]
   "Buy More"])
