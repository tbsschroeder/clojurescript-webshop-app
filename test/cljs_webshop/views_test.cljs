(ns cljs-webshop.views-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [cljs-webshop.floater :as blocks]))

;https://github.com/day8/re-frame/blob/master/docs/Testing.md
;https://github.com/day8/re-frame-test

(deftest test-numbers
  (testing "fake on"
    (is (= 1 1))))

(deftest weather-component-test-in
  (testing " WHEN render component in test"
    (let [comp (r/render-component [blocks/shop test-state]
                                   (. js/document (getElementById "app")))]
      ;;ASSERT
      (is (= (d/html (sel1 :#city)) "Paris"))
      (is (= (d/html (sel1 :#temp)) "12")))))

;(defn subscription-stub [x]
;  (atom
;    (case x
;      [:query-id] 42)))
;
;(deftest some-test
;  (with-redefs [re-frame/subscribe (subscription-stub)]
;               (testing "some some view which does a subscribe"
;                 ..... call the view function and the hiccup output)))