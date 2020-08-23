(ns app.logic.core
  (:require [ysera.random :refer [random-nth
                                  get-random-int]]
            [ysera.test :refer [is=
                                error?]]
            [ysera.error :refer [error]]))

(defn create-empty-app-state
  {:test (fn []
           (is= (create-empty-app-state)
                {:count 0
                 :seed 35651602
                 :is-drawer-open false
                 :is-modal-open false
                 :todo-list []}))}
  []
  {:count 0
   :seed 35651602
   :is-drawer-open false
   :is-modal-open false
   :todo-list []})

(defn create-app-state
  "Creates the app state"
  {:test (fn []
           (is= (create-app-state :count 100 :todo-list [{:id 1 :title "title" :description "description"}])
                {:count 100
                 :seed 35651602
                 :is-drawer-open false
                 :is-modal-open false
                 :todo-list [{:id 1 :title "title" :description "description"}]}))}
  [& kvs]
  (let [state (create-empty-app-state)]
    (if (empty? kvs)
      state
      (apply assoc state kvs))))

(defn create-to-do
  "Creates a new to-do object" 
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
  "Adds a new todo with the given title and description to the todo list"
  {:test (fn [] 
           (is= (-> (create-app-state :todo-list [{:id 1 :title "title" :description "description"}])
                    (add-new-to-do "new title" "new description")
                    (:todo-list)
                    (count))
                2))}
  [state title description]
  (let [max-number 1000000
        [seed random-int] (get-random-int (:seed state) max-number)]
    (-> (update state :todo-list (fn [list] 
                                   (conj list (create-to-do random-int title description))))
        (assoc :seed seed))))