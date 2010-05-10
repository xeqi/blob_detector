(ns blob-detector.label-map
  (:use blob-detector.blob))

(defn combine
  [m ids id blob]
  (assoc (apply dissoc m ids) id (join (conj (map #(get m %) ids) blob))))

(defn search
  [m k]
  (if (= k (get m k))
    k
    (recur m (get m k))))