(ns conexao-local.first-transactions
  (:require [datomic.api :as d]))

(defn nome-aleatorio []
  (let [nomes ["João" "Maria" "Pedro" "Ana" "Luiza" "Lucas" "Sofia" "Mateus" "Miguel" "Arthur" "Helena" "Helena" "Laura" "Davi" "Samuel"
               "Gabriel" "Cecilia" "Alexandre" "Sônia" "Joaquim" "Rafael" "Rodrigo" "Daniel" "Edson" "Sérgio" "Fabio" "Sandra" "Marcela"
               "Marcos" "Raimundo" "Sebastião"]
        nome (rand-nth nomes)]
    nome))



(defn popular-banco-aleatorio [num-project]
  (doall
    (repeatedly num-project
                (fn []
                  {:project/title           (nome-aleatorio)
                   :project/people-quantity (rand-int 100)}))))


(defn get-all-projects [conn]
  (d/q '[:find ?entidade ?titulo ?quantidade
         :keys id titulo quantidade
         :where
         [?entidade :project/title ?titulo]
         [?entidade :project/people-quantity ?quantidade]
         ] (d/db conn)))

(defn get-by-id [conn id]
  (d/q '[:find ?id ?titulo ?quantidade
         :in $ ?id
         :keys id titulo quantidade
         :where
         [?id :project/title ?titulo]
         [?id :project/people-quantity ?quantidade]
         ] (d/db conn) id))

;contruir 2 consultas , uma pega o dado pelo titulo passado e o outro pela quantidade

(defn get-num-registros [conn]
  (d/q '[:find (count ?entidade)
         :keys max
         :where
         [?entidade :project/title ?titulo]
         ] (d/db conn)))

(defn get-min-max-quantidade [conn]
  (d/q '[:find (avg ?quantidade) (median ?quantidade) (min ?quantidade) (max ?quantidade)
         :keys avg median min max
         :where
         [?entidade :project/title ?titulo]
         [?entidade :project/people-quantity ?quantidade]
         ] (d/db conn)))

(defn get-n-min-max [conn]
  (d/q '[:find(min 4 ?quantidade) (max 4 ?quantidade)
         :keys min max
         :where
         [?entidade :project/title ?titulo]
         [?entidade :project/people-quantity ?quantidade]
         ] (d/db conn)))


;(min ?xs)
;(max ?xs)
;(count ?xs)
;(count-distinct ?xs)
;(sum ?xs)
;(avg ?xs)
;(median ?xs)
;(variance ?xs)
;(stddev ?xs)
;(distinct ?xs)
;(min n ?xs)
;(max n ?xs)
;(rand n ?xs)
;(sample n ?xs)

(defn get-quantidade-menorq [conn]
  (d/q '[:find ?entidade ?titulo ?quantidade
         :in $
         :keys entidade titulo quantidade
         :where
         [?entidade :project/title ?titulo]
         [?entidade :project/people-quantity ?quantidade]
         [(< ?quantidade 5)]
         ] (d/db conn)))

(defn get-usando-not-in [conn]
  (d/q '[:find ?entidade ?titulo ?quantidade
         :in $
         :keys entidade titulo quantidade
         :where
         [?entidade :project/title ?titulo]
         [?entidade :project/people-quantity ?quantidade]
         [(< ?quantidade 5)]
         (not [?entidade :project/people-quantity 3])
         ] (d/db conn)))
