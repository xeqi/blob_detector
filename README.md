# blob_detector

A simple blob detector for clojure.

## Usage

The useful function is `blob-detector.core/detect` which requires a predicate taking a x and y argument and determining if they are interesting, and a width and height of the 2d plane.

For example:

    (import 'java.io.File)
    (import 'java.awt.BufferedImage)
    (import 'javax.imageio.ImageIO)
    (import 'java.awt.Color)
    
    (defn greenish
       [#^BufferedImage image]
       (fn [x y] (let [color (Color. (.getRGB image x y))
                       red (.getRed color)
                       green (.getGreen color)
                       blue (.getBlue color)]
                      (and (> green (+ 5 blue))
                           (> green (+ 5 red))))))
    
    (let [image (ImageIO/read (File. "somefile.png"))]
         (blob-detector.core/detect (greenish image) (.getWidth image) (.getHeight image)))