(ns cljs-webshop.ajax-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [cljs-webshop.events :as ajax]))

(deftest test-numbers
  (is (= 1 1)))

(deftest test-async
  (let [url0 "http://foo.com/api.edn"
        url1 "http://bar.com/api.edn"
        res0 (http/get url0)
        res1 (http/get url1)]
    (async done
           (go
             (is (= (<! res0) :awesome))
             (is (= (<! res1) :cool))
             (done)))))