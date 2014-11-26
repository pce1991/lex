(defproject lex "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-wordnet "0.1.1-SNAPSHOT"]
                 [instaparse "1.3.4"]]
  :main ^:skip-aot lex.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
