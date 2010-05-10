(ns blob-detector.core-test
  (:use [blob-detector.core]
	[blob-detector.cache.vector] :reload-all)
  (:use [clojure.test]))

(deftest simple-detect 
  (let [accum (detect (fn [x] true) (fn [x y] 1) (blob-detector.cache.vector.Vector. [] 10 1))]
    (is (= (count (:blobs accum))
	   1))
    (is (= (blob-detector.blob.Blob. 0 0 10 1 10)
	   (get (:blobs accum) 1)))))

