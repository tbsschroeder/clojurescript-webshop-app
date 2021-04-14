(ns cljs-webshop.blocks-test
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [cljs.test :refer-macros [deftest is testing run-tests]]
            [cljs-webshop.blocks :as blocks]))

(defonce test-state (r/atom {:error    {:status 0 :text ""}
                             :warning  ""
                             :articles []
                             :debug    false
                             :page     "shop"}))

(deftest test-numbers
  (is (= 1 1)))

(deftest weather-component-test-in
  ;;WHEN render component in test
  (let [comp (r/render-component [blocks/shop test-state w/weather-component]
                                 (. js/document (getElementById "test")))]
    ;;ASSERT
    (is (= (d/html (sel1 :#city)) "Paris"))
    (is (= (d/html (sel1 :#temp)) "12"))))