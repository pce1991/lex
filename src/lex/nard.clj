(ns lex.nard)

;;; convert english to a regex and viceaversa. approarch?
;;; patterns maybe? look at Novak's assignment

;;; cfg of english which once generated, the nodes can be viewed to
;;; find the structure of the regex
;;; if english phrases correspond to patterns that're regexes, then you can
;;; parse out the data seperately and then combine it with the tags.
;;; "Any number of 'a's" would be parsed as [:* [:characters 'a']]
(def english-parser
  (parser "S = Epsilon"))

