(ns cljs-webshop.db)

(defn default-db [db]
  (-> db
      (assoc :error {:status 0 :text "a"})
      (assoc :warning "")
      (assoc :articles [])
      (assoc :container "shop")
      (assoc :detail nil)
      (assoc :search-term nil)))