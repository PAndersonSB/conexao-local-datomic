(ns conexao-local.core
  (:require [datomic.api :as d]
            [conexao-local.first-transactions :as myt]
            ))

;bin/console -p 8080 dev datomic:dev://localhost:4334/
;bin/transactor -Ddatomic.printConnectionInfo=true config/dev-transactor-template.properties

(def db-uri "datomic:dev://localhost:4334/hello")
;(println(d/delete-database db-uri))
;(println(d/create-database db-uri))
(defn abre-conexao [] (d/connect db-uri))

(def project
  [{:db/ident       :project/title
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "The title of the project"}

   {:db/ident       :project/people-quantity
    :db/valueType   :db.type/long
    :db/cardinality :db.cardinality/one
    :db/doc         "The quantity of people involved in the project"}
   ])

;(d/transact (abre-conexao) project)
;(d/transact (abre-conexao) edital)
;(d/transact (abre-conexao) (myt/popular-banco-aleatorio 500))
;(d/transact (abre-conexao) (myt/popular-banco-aleatorio 100))

;(println (myt/get-all-projects (abre-conexao)))
;(println (myt/get-by-id (abre-conexao) 17592186045531))
;(println (myt/get-num-registros (abre-conexao)))
;(println (myt/get-min-max-quantidade (abre-conexao)))
;(println (myt/get-n-min-max (abre-conexao)))
;(println (myt/get-teste (abre-conexao)))
(println (myt/get-usando-not-in (abre-conexao)))

