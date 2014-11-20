(ns lex.utils)

(defn read-seq
  "Opens a files and line-seqs on the rdr"
  [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (into [] (line-seq rdr))))

