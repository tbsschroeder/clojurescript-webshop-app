(ns cljs-webshop.blocks-test
  (:require [reagent.core :as r]
            [cljs.test :refer-macros [deftest is testing run-tests]]
            [cljs-webshop.views :as blocks]))

(defonce test-state (r/atom {:error    {:status 0 :text ""}
                             :warning  ""
                             :articles []
                             :debug    false
                             :page     "shop"}))

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