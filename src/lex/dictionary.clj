(ns lex.dictionary
  (:require [clj-wordnet.core]
            [clj-wordnet.similarity.algo.hso]))

(def wordnet (make-dictionary "resources/WordNet-3.0/dict"))

(defn wn 
  "Return all entries for a word."
  ([word] (if-not (empty?  (wordnet word))
            (wordnet word)
            nil))
  ([word pos] (if-not (empty? (wordnet word pos))
                (wordnet word pos)
                nil)))

;;; get adjacent words, everything with a distance of one. Too expensive? 

;;; return the similarity between two words

;;; return the most similar word to a sequence of words. Sum the similarity and path distance between a word and the seq; that's the word you want. I want the know the word that's like freak and novelty, which it fails to do, so I need to cast a wider net perhaps? To do this I'll need a list of all the words in WN, or within a category. 

;;; how to go from ID to entry

(defn re-search
  "Searches wordnet for all entries matching the regex."
  [pattern])

(defn search
  "Searches wordnet for all entries that contain one of the supplied words as either a synonym or within it's gloss."
  [& words])
