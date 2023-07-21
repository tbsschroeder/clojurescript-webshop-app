(ns cljs-webshop.event-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [cljs-webshop.events :as events]
            [day8.re-frame.test :as rf-test]))


;; https://github.com/day8/re-frame/blob/master/docs/Testing.md

(deftest display-warning-test
  (rf-test/run-test-sync
    (rf/dispatch [:initialize-db])
    (let [warning (rf/subscribe [:warning])]
      (is (= 1 1))
      (is (= "" @warning)))))

(deftest display-error-test
  (rf-test/run-test-sync
    (let [db (-> {}
                 (events/initialize-db [:initialize-db]))
          event [:indisplay-error :other :stuff]
          db' (display-error)]
      (is (= 1 1))
      (is (= (:error db) ""))))) )

(deftest clear-warning-test
  (rf-test/run-test-sync
    (let [db (-> {}
                 (events/initialize-db [:initialize-db]))
          event [:inclear-warning :other :stuff]
          db' (clear-warning)]
      (is (= 1 1))
      (is (= (:warning db) ""))))) )

(deftest clear-error-test
  (rf-test/run-test-sync
    (let [db (-> {}
                 (events/initialize-db [:initialize-db]))
          event [:clear-error :other :stuff]
          db' (clear-error db event)]
      (is (= 1 1))
      (is (= (:error db) ""))))) )

(deftest change-page-test
  (rf-test/run-test-sync
    (let [db (-> {}
                 (events/initialize-db [:initialize-db]))
          event [:change-page :other :stuff]
          db' (change-page db event)]
      (is (= 1 1))
      (is (= (:container db) ""))))) )