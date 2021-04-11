(ns clojurescript.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [cljs.core.async :refer [<!]]
            [cljs-http.client :as http]
            [clojurescript.blocks :as blocks]
            )
  (:require-macros [cljs.core.async.macros :refer [go]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (r/atom {:text "Hello world!"}))
(defonce error (r/atom {:status 0 :text ""}))
(defonce articles (r/atom [{:id 1, :title "Bacon", :description "Bacon is a type of salt-cured pork. Bacon is prepared from several different cuts of meat, typically from the pork belly or from back cuts, which have less fat than the belly. And it's delicious!", :category "Meat", :image "img/products/bacon.jpg", :count 0} {:id 2, :title "Crinkle Cut Fries", :description "Crinkle-cutting is slicing that leaves a corrugated surface. This is done with corrugated knives or mandoline blades. Crinkle-cut potato chips are sometimes called ruffled.", :category "Potato Products ", :image "img/products/crinklefries.jpg", :count 0} {:id 3, :title "Deep Frying Fat", :description "Deep fat frying is a cooking method that can be used to cook food. The process involves submerging a food in extremely hot oil until it reaches a safe minimum internal temperature.", :category "Oil & Fat", :image "img/products/fryingfat.jpg", :count 0} {:id 4, :title "Garlic", :description "Garlic is a species in the onion genus. Its close relatives include the onion, shallot, leek, chive, and chinese onion. Garlic is native to central asia and ...", :category "Vegetables", :image "img/products/garlic.jpg", :count 0} {:id 5, :title "Ice Cream", :description "Ice cream is a sweetened frozen food typically eaten as a snack or dessert. It may be made from dairy milk or cream and is flavored with a sweetener and any spice.", :category "Sweets", :image "img/products/icecream.jpg", :count 0} {:id 6, :title "Olive Oil", :description "Olive oil is a liquid obtained from olives. The oil is produced by pressing whole olives. It is commonly used in cooking, whether for frying or as a salad dressing.", :category "Oil & Fat", :image "img/products/oliveoil.jpg", :count 0} {:id 7, :title "Shrimps", :description "Shrimp is shrimp and we do not care whether they are black tiger, white tiger or sea tiger. Just cook or grill them with a lot of garlic and, if you like, with a steak!", :category "Fish", :image "img/products/shrimps.jpg", :count 0} {:id 8, :title "Steakhouse Fries", :description "Steakhouse fries offer an intense potato experience. Made from whole potatoes extra wide cut, they are especially potato and go perfectly with hearty meat dishes.", :category "Potato Products", :image "img/products/steakhousefries.jpg", :count 0} {:id 9, :title "Sweet Potato Fries", :description "Fried sweet potato features in a variety of dishes and cuisines including the popular sweet potato fries, a variation of French fries using sweet potato instead of potato.", :category "Potato Products", :image "img/products/sweetpotatoefries.jpg", :count 0} {:id 10, :title "Tortilla Chips", :description "A tortilla chip is a snack food made from corn tortillas, which are cut into wedges and then fried—or baked. Corn tortillas are made of corn, vegetable oil, salt and water.", :category "Snack", :image "img/products/tortilla.jpg", :count 0}]))
(defonce debug true)

(defn reset-error! []
  (reset! error {:status 0 :text ""}))

(defn reset-articles! []
  (reset! articles []))

(defn error-callback [status body]
  (when debug (prn "error-callback"))
  (swap! error assoc :status status)
  (swap! error assoc :text body))

(defn success-callback [status body]
  (do
    (when debug (prn "success-callback"))
    (reset-error!)
    (reset! articles (:articles body))
    ))

(defn get-all-articles "Get all articles from localhost" []
  (when debug (prn "get-all-articles"))
  (go (let [response (<! (http/get "http://localhost:8080/api/article/all"))
            status (:status response)
            body (:body response)]
        (if (= 200 status)
          (success-callback status body)
          (error-callback status body)))))

(defn error-p []
  (let [error-str (if (zero? (:status @error))
                    ""
                    (str (:status @error) ": " (:text error)))]
    [:p.center.text-warning error-str]))

(defn shop []
  [:div
   (blocks/button->checkout @articles)
   [:hr {:style {:margin "2rem 0" :background-color "white"}}]
   ;(vec (conj (blocks/article->big-cards @articles) :tbody))
   (blocks/article->big-cards @articles)

   [:hr {:style {:margin "2rem 0" :background-color "white"}}]
   ])

(defn simple-body []
  [:div
   [error-p]
   [shop]])

;; MAIN
(rdom/render [simple-body]
             (. js/document (getElementById "app")))

(get-all-articles)


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
