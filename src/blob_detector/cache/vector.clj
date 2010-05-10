(ns blob-detector.cache.vector
  (:use blob-detector.cache.protocol))

(deftype Vector [vec width height]
  Cache
  (insert
   [this x y val]
   (Vector. (assoc vec (+ x (* y width)) val) width height))
  (lookup
   [this x y]
   (if (or (< x 0)
	   (< y 0)
	   (>= x width)
	   (>= y height)
	   (> (+ x (* y width)) (count vec)))
     0
     (nth vec (+ x (* y width)))))
  (width [this] width)
  (height [this] height))