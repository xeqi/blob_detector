(ns blob-detector.blob)

(defrecord Blob [x y width height mass])

(defn blob-merge 
  [b b2]
  (let [x (:x b)
	x2 (:x b2)
	y (:y b)
	y2 (:y b2)]
    (Blob. (min x x2)
	   (min y y2)
	   (- (max (+ x (:width b)) (+ x2 (:width b2))) (min x x2))
	   (- (max (+ y (:height b)) (+ y2 (:height b2))) (min y y2))
	   (+ (:mass b) (:mass b2)))))

(defn join
  [blobs]
  (reduce blob-merge blobs))

