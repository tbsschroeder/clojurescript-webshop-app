(ns cljs-webshop.ajax
  (:require [cljs.core.async :refer [<!]]
            [cljs-http.client :as http])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def api "http://localhost:8080/api")

(def urls {:all-articles (str api "/article/all")
           :inc-article  (str api "/article/inc")
           :dec-article  (str api "/article/dec")
           :rem-article  (str api "/article/rem")})

(defn- reset-error! [app-state]
  (swap! app-state assoc :error {:status 0 :text ""}))

(defn- reset-warning! [app-state]
  (swap! app-state assoc :warning ""))

(defn- reset-status! [app-state]
  (reset-error! app-state)
  (reset-warning! app-state))

;; Generic callbacks
(defn- error-callback [app-state status body]
  (when (:debug @app-state) (prn "error-callback"))
  (if (= 0 status)
    (swap! app-state assoc :error {:status "?" :text "unknown error"})
    (swap! app-state assoc :error {:status status :text body})))

(defn- success-callback-articles [app-state body]
  (do
    (when (:debug @app-state) (prn "success-callback-articles"))
    (reset-status! app-state)
    (swap! app-state assoc :articles (:articles body))))

;; Async requests
(defn get-all-articles "Get all articles from localhost"
  [app-state]
  (when (:debug @app-state) (prn "get-all-articles"))
  (go (let [response (<! (http/get (:all-articles urls)))
            status (:status response)
            body (:body response)]
        (if (= 200 status)
          (success-callback-articles app-state body)
          (error-callback app-state status body)))))

(defn manipulate-article "Manipulates amount of an article"
  [app-state id key]
  (when (:debug @app-state) (prn (str id " " key)))
  (go (let [response (<! (http/post (key urls)
                                    {:form-params {:id id}}))
            status (:status response)
            body (:body response)]
        (if (= 200 status)
          (success-callback-articles app-state body)
          (error-callback app-state status body)))))