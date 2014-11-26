(ns lex.dictionary
  (:require [clj-wordnet.core :refer :all]
;            [clj-wordnet.similarity.algo.hso :refer :all]
            [lex.utils :refer :all]))

;;; The major con of this is that it only works with wordnet. I could implement searches to just travel an arbitrary list of words, but it retriev the entries.
;;; What really needs to be done is merge wordnet and OED.
;;; This also depends on having an index of every word, which I cant get for the OED or websters
;;; I can use a list like UNIX words tho and just search each in websters (which is agaisnt the rules :(

(def wordnet (make-dictionary "resources/WordNet-3.0/dict"))

(defn wn 
  "Return all entries for a word."
  ([word] (if-not (empty?  (wordnet word))
            (wordnet word)
            nil))
  ([word pos] (if-not (empty? (wordnet word pos))
                (wordnet word pos)
                nil)))


;;; should the strings have their POS appended like dog#n dog#v?
(defn index []
  "Returns a lazy-seq of strings where each is an entry in wordnet."
  ;; we call nthrest so it throws out the License at the beiginning of each index file
  (let [nouns (nthrest (read-seq "resources/WordNet-3.0/dict/index.noun") 29)
        verbs (nthrest  (read-seq "resources/WordNet-3.0/dict/index.verb") 29)
        adjectives (nthrest (read-seq "resources/WordNet-3.0/dict/index.adj") 29)
        adverbs (nthrest (read-seq "resources/WordNet-3.0/dict/index.adv") 29)
        words (concat nouns verbs adjectives adverbs)]
    (letfn [(just-word [entry] 
              (let [seperated (clojure.string/split entry #" ")]
                (first seperated)))] 
      (distinct (mapv just-word words)))))  ;distinct so dog noun and dog verb dont appear twice.         

(def INDEX (index))

;;; get adjacent words, everything with a distance of one. Too expensive? 

;;; return the similarity between two words

;;; return the most similar word to a sequence of words. Sum the similarity and path distance between a word and the seq; that's the word you want. I want the know the word that's like freak and novelty, which it fails to do, so I need to cast a wider net perhaps? To do this I'll need a list of all the words in WN, or within a category. 

;;; how to go from ID to entry

(defn search-pattern
  "Searches wordnet for all entries in which the regex is found, returning a seq of them."
  [pattern]
  (filter #(re-find pattern %) INDEX))

(defn search-patterns [patterns]
  "Returns a map of which pattern keyed to all the words containing it.")

(defn re-find-all [pats str]
  "retruns a vector of all the patterns found in str")

(defn re-find-some [pats str]
  "Returns the pattern which was successfully found in str"
  (some (fn [pat] (if (re-find pat str)
                    pat
                    false))
        pats))

;;; only works for wordnet, so build a more general one (but there'll be
;;; multipl glosses for a word.
;;; quite slow! find a way to speed up! what's slowing it down the most?
(defn search
  "Searches wordnet for all entries that contain one of the supplied words as either a synonym or within it's gloss."
  [& words]
  (loop [index INDEX
         matches {}]
    (if (empty? index)
      matches
      (let [entry (wn (first index))]
        (if (map? entry)
          ;; check that entry and recur
          (if (or (re-find-some words (first index))
                  (re-find-some words (:gloss entry)))
            (recur (rest index) (assoc matches (first index) entry))
            (recur (rest index) matches))
          ;else loop thru the multiple entries. will need to recur on what this returns
          (recur (rest index)
                 (loop [entries entry   ;put into a seq since rest will
                        m matches]  
                   (if (empty? entries)
                     m
                     (let [e (first entry)]
                       (if (or (re-find-some words (first index))  ;get it from e instead?
                               (re-find-some words (:gloss e)))
                         (recur (rest entries) (assoc m (first index) e))
                         (recur (rest entries) m)))))))))))

(defn match
  "Will only return words found in INDEX that match the pattern"
  [pattern]
  (filter #(re-matches pattern %) INDEX))

