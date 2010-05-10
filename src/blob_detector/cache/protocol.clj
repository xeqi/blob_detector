(ns blob-detector.cache.protocol)

(defprotocol Cache
  (insert [this x y val])
  (lookup [this x y])
  (height [this])
  (width [this]))