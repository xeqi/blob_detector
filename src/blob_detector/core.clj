(ns blob-detector.core
  (:use [blob-detector.label-map]
	[blob-detector.blob]
	[blob-detector.cache.protocol]))

(defn- label-point
  [x y accum]
  (let [label-map (:label-map accum)
	next-label (:next-label accum)
	blobs (:blobs accum)
	cache (:cache accum)
	nearby-blob-ids (distinct (filter #(not= 0 %)
					  [(search label-map (lookup cache (dec x) (dec y)))
					   (search label-map (lookup cache x       (dec y)))
					   (search label-map (lookup cache (inc x) (dec y)))
					   (search label-map (lookup cache (dec x) y))]))]
    (if (empty? nearby-blob-ids)
      {:cache (insert cache x y next-label)
       :label-map (assoc label-map next-label next-label)
       :next-label (inc next-label)
       :blobs (assoc blobs next-label (blob-detector.blob.Blob. x y 1 1 1))}
      (let [label (apply min nearby-blob-ids)]
       	{:cache (insert cache x y label)
	 :label-map (reduce #(assoc %1 %2 label) label-map nearby-blob-ids)
	 :next-label next-label
	 :blobs (combine blobs nearby-blob-ids label (blob-detector.blob.Blob. x y 1 1 1))}))))

(defn- label-row
     [pred get-data y width accum]
     (reduce #(if (pred (get-data %2 y))
		  (label-point %2 y %1)
		  (assoc %1 :cache (insert (:cache %1) %2 y 0)))
	     accum
	     (range width)))

(defn- label-data
     [pred get-data cache width height]
     (reduce #(label-row pred get-data %2 width %1)
	     {:label-map {0 0} :next-label 1 :blobs {} :cache cache}
	     (range height)))

(defn detect
  [pred get-data cache]
  (label-data pred get-data cache (width cache) (height cache)))