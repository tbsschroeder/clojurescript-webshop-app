(ns cljs-webshop.floater
  (:require [re-frame.core :as rf]))

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
                        :on-click     #(rf/dispatch [:clear-warning])}
         [:span {:aria-hidden "true"} "×"]]])]))