(ns cljs-webshop.events
  (:require [ajax.core :refer [json-request-format json-response-format]]
            [cljs-webshop.db :as db]
            [clojure.string :as str]
            [day8.re-frame.http-fx]
            [re-frame.core :as rf]))

(def api-url "http://localhost:8080/api")

(defn- endpoint
  "Concat any params to api-url separated by /"
  [& params]
  (str/join "/" (concat [api-url] params)))

(rf/reg-event-fx
 :get-all-articles
 (fn [db event]
   {:http-xhrio {:method          :get
                 :uri             (endpoint "article/all")
                 :timeout         5000
                 :format          (json-request-format)
                 :response-format (json-response-format {:keywords? true})
                 :on-success      [:process-response]
                 :on-failure      [:display-error]}}))

(rf/reg-event-fx
 :increase-article
 (fn [db [_ id]]
   {:http-xhrio {:method          :post
                 :uri             (endpoint "article/inc")
                 :body            (str "id=" id)
                 :timeout         5000
                 :format          (json-request-format)
                 :response-format (json-response-format {:keywords? true})
                 :on-success      [:process-response]
                 :on-failure      [:display-error]}}))

(rf/reg-event-fx
 :decrease-article
 (fn [db [_ id]]
   {:http-xhrio {:method          :post
                 :uri             (endpoint "article/dec")
                 :body            (str "id=" id)
                 :timeout         5000
                 :format          (json-request-format)
                 :response-format (json-response-format {:keywords? true})
                 :on-success      [:process-response]
                 :on-failure      [:display-error]}}))

(rf/reg-event-fx
 :remove-article
 (fn [db [_ id]]
   {:http-xhrio {:method          :post
                 :uri             (endpoint "article/rem")
                 :body            (str "id=" id)
                 :timeout         5000
                 :format          (json-request-format)
                 :response-format (json-response-format {:keywords? true})
                 :on-success      [:process-response]
                 :on-failure      [:display-error]}}))

(rf/reg-event-fx
 :initialize-db
 (fn [db _]
   {:db (db/default-db db)}))

(rf/reg-event-db
 :process-response
 (fn [db [_ response]]
   (do
     (rf/dispatch [:clear-error])
     (rf/dispatch [:clear-warning])
     (rf/dispatch [:display-articles (:articles response)]))))

(rf/reg-event-db
 :display-articles
 (fn [db [_ val]]
   (assoc db :articles val)))

(rf/reg-event-db
 :display-warning
 (fn [db [_ val]]
   (assoc db :warning val)))

(rf/reg-event-db
 :display-error
 (fn [db [_ status text]]
   (if (zero? status)
     (assoc db :error {:status "?" :text "unknown error"})
     (assoc db :error {:status status :text text}))))

(rf/reg-event-db
 :clear-warning
 (fn [db _]
   (assoc db :warning "")))

(rf/reg-event-db
 :clear-error
 (fn [db _]
   (assoc db :error {:status 0 :text "b"})))

(rf/reg-event-db
 :change-page
 (fn [db [_ val id]]
   (assoc db :detail id
             :container val)))
