(ns lex.gcide
  (:require [instaparse.core :refer :all]))

(def D (slurp "resources/gcide-0.51/CIDE.D"))

;;; define a grammar for the structure.
;;; the grammar has a body bookended by p, then a content section which
;;; can include any amount of content denoted with <x>content</x> for all
;;; markers, but between those tags there can be text or content
;;; the regex should match anything that doesnt begin with a < or end >
;;; the tagset is huge! how can I
;;; lots of info I dont care about: images, tables, how do I parse it and tell
;;; it to throw this away. I could dump it all in a miscellania.
;;; remember, regexes are greedy! risk using up more tokens than you want too
;;; add more marks to text and misc
;;; I want it to throw out the actual tags and just give :definition []
(def gcide-parser
  (parser
   "S = body | Epsilon
   body = <'<p>'> content <'</p>'>
   content = Epsilon | (misc | entry | headword | pronunciation | pos | etymology | definition | source | field | collocation-section | collocation-def | collocation-entry | bold) content | text
   pos = <'<pos>'> content <'</pos>'>
   entry = <'<ent>'> content <'</ent>'>
   headword = <'<hw>'> content <'</hw>'>
   pronunciation = <'<pr>'> content <'</pr>'>
   etymology = <'<ety>'> content <'</ety>'>
   definition = <'<def>'> content <'</def>'>
   source = <'<ets>'> content <'</ets>'>
   field = <'<fld>'> content <'</fld>'>
   collocation-section = <'<cs>'> content <'</cs>'>
   collocation-def = <'<cd>'> content <'</cd>'>
   collocation-entry = <'<col>'> content <'</col>'>
   bold = Epsilon (* what does bold mean? *)
   misc = #'<[a-z]*>' content #'</[a-z]*>' (* i actually do want tags here *)
   <text> = #'[a-zA-Z\\ ]*'"))


