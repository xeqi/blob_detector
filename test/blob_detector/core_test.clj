(ns blob-detector.core-test
  (:use [blob-detector.core] :reload-all)
  (:use [clojure.test]))

(defn- all
  [blobs expected]
  (dorun (map #(is (some #{%} blobs)) expected)))

(deftest detect-line 
  (let [blobs (detect (fn [_ _] true) 10 1)]
    (is (= (count blobs) 1))
    (is (every? (set blobs) [(blob-detector.blob.Blob. 0 0 10 1 10)]))))

(deftest detect-multiple-lines
  (let [blobs (detect (fn [x _] (not= 0 (mod x 3))) 10 1)]
    (is (= (count blobs) 3))
    (all blobs [(blob-detector.blob.Blob. 1 0 2 1 2)
		(blob-detector.blob.Blob. 4 0 2 1 2)
		(blob-detector.blob.Blob. 7 0 2 1 2)])))

(deftest detect-multiple-columns
  (let [blobs (detect (fn [x _] (not= 0 (mod x 3))) 9 9)]
    (is (= (count blobs) 3))
    (all blobs [(blob-detector.blob.Blob. 1 0 2 9 18)
		(blob-detector.blob.Blob. 4 0 2 9 18)
		(blob-detector.blob.Blob. 7 0 2 9 18)])))