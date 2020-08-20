(ns app.logic.core
  (:require [ysera.random :refer [random-nth
                                  get-random-int]]
            [ysera.test :refer [is=
                                error?]]
            [ysera.error :refer [error]]))

(defn create-app-state
  []
  {:count 0
   :seed 1
   :is-drawer-open false
   :is-modal-open false})

(defn create-to-do
  "Creates a to-do object" 
  {:test (fn [] 
           (is= (create-to-do 1 "title" "description")
                {:id 1
                 :title "title"
                 :description "description"})
           (error? (create-to-do "999" 123 321)))}
  [id title description]
  (when (not (and (number? id) (string? title) (string? description)))
    (error "bad input"))
  {:id id
   :title title
   :description description})

(defn add-new-to-do
  [state title description]
  (let [max-number 1000000
        [seed random-int] (get-random-int (:seed state) max-number)]
    (-> (update state :to-dos (partial conj (create-to-do random-int title description)))
        (assoc :seed seed))))