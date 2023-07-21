(ns cljs-webshop.subs-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [cljs-webshop.subs :as subs]
            [day8.re-frame.test :as rf-test]))

(deftest test-numbers
  (testing "fake on"
    (is (= 1 1))))

(deftest app-initital-state
  (testing "Init app state"
    (rf-test/run-test-sync
      (let [articles (rf/subscribe [:articles])
            error (rf/subscribe [:error])
            warning (rf/subscribe [:warning])
            page (rf/subscribe [:container])
            basket (rf/subscribe [:basket])
            basket-count (rf/subscribe [:basket-count])
            get-article (rf/subscribe [:get-article])]

        ;;Assert the initial state
        (is (empty? @articles))
        (is (empty? @error))
        (is (empty? @warning))
        (is (empty? @page))
        (is (empty? @basket))
        (is (empty? @basket-count))
        (is (empty? @get-article)))))
