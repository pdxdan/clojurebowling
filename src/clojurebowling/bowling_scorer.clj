(ns clojurebowling.bowling-scorer
  (:require [clojure.test :refer :all]
            [clojurebowling.bowling-scorer :as bowling-scorer]))

(defn is-strike
  "Returns true if the first roll in a frame was 10"
  [rolls]
  (= (first rolls) 10))

(defn is-spare
  "Returns true when two rolls addi up to 10, if the first roll was not a 10"
  [rolls]
  (and (> (count rolls) 1)
       (not= (first rolls) 10)
       (= (+ (first rolls) (first (rest rolls))) 10)))

(defn rolls-for-frame
  "Returns a list of rolls to count for the frame"
  [rolls]
  (cond
    (is-strike rolls) (take 3 rolls)
    (is-spare rolls) (take 3 rolls)
    :else (take 2 rolls)))

(defn remaining-rolls
  "Returns the remaining rolls to be scored after this frame"
  [rolls]
  (if (is-strike rolls)
    (drop 1 rolls)
    (drop 2 rolls)))

(defn to-frames
  "Returns a list of scores for each frame"
  ([rolls]
    (to-frames rolls []))
  ([rolls frames]
    (if (empty? rolls)
      (flatten frames)
      (recur (remaining-rolls rolls) (conj frames (reduce + (rolls-for-frame rolls)))))))

(defn score
  "Compute the total score for a given set of rolls"
  [rolls]
  (reduce + (take 10 (to-frames rolls))))