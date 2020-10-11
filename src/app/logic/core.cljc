(ns app.logic.core
  (:require [ysera.random :refer [get-random-int]]
            [ysera.test :refer [is=
                                is
                                error?]]
            [ysera.error :refer [error]]))

(defn create-empty-app-state
  {:test (fn []
           (is= (create-empty-app-state)
                {:count 0
                 :seed 35651602
                 :todo-list []}))}
  []
  {:count 0
   :seed 35651602
   :todo-list []})

(defn create-app-state
  "Creates the app state"
  {:test (fn []
           (is= (create-app-state :count 100 :todo-list [{:id 1 :title "title" :description "description" :done false}])
                {:count 100
                 :seed 35651602
                 :todo-list [{:id 1 :title "title" :description "description" :done false}]}))}
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
                 :description "description"
                 :done false})
           (error? (create-to-do "999" 123 321)))}
  [id title description]
  (when (not (and (number? id) (string? title) (string? description)))
    (error "bad input"))
  {:id id
   :title title
   :description description
   :done false})

(defn get-todo-list
  {:test (fn []
           (is= (-> (create-app-state :todo-list [{:id 1 :title "title" :description "description"}])
                    (get-todo-list))
                [{:id 1 :title "title" :description "description"}]))}
  [state]
  (:todo-list state))

(defn set-todo-list
    {:test (fn []
             (is= (-> (create-app-state :todo-list [])
                      (set-todo-list [{:id 1 :title "title" :description "description"}]))
                  (create-app-state :todo-list [{:id 1 :title "title" :description "description"}])))}
  [state todo-list]
  (assoc state :todo-list todo-list))

(defn add-new-to-do
  "Adds a new todo with the given title and description to the todo list"
  {:test (fn [] 
           (is= (-> (create-app-state :todo-list [{:id 1 :title "title" :description "description"}])
                    (add-new-to-do "new title" "new description")
                    (get-todo-list)
                    (count))
                2))}
  [state title description]
  (let [max-number 1000000
        [seed random-int] (get-random-int (:seed state) max-number)]
    (-> (update state :todo-list (fn [list] 
                                   (conj list (create-to-do random-int title description))))
        (assoc :seed seed))))

(defn remove-todo
  [state id]
  (->> (get-todo-list state)
      (filter (fn [todo-item] 
                (not (= id (:id todo-item)))))
       (set-todo-list state)))

(defn get-todo-by-id
    {:test (fn []
             (is= (-> (create-app-state :todo-list [{:id 1 :title "title" :description "description" :done false}
                                                    {:id 2 :title "title" :description "description" :done false}
                                                    {:id 3 :title "title" :description "description" :done false}])
                      (get-todo-by-id 2))
                  {:id 2 :title "title" :description "description" :done false})
             (is= (-> (create-app-state :todo-list [])
                      (get-todo-by-id 1))
                  nil))}
  [state id]
    (->> (get-todo-list state)
         (filter (fn [todo-item]
                   (= id (:id todo-item))))
         (first)))

(defn switch-todo-done-state
  {:test (fn []
           (is (-> (create-app-state :todo-list [{:id 1 :title "title" :description "description" :done false}
                                                  {:id 2 :title "title" :description "description" :done false}
                                                  {:id 3 :title "title" :description "description" :done false}])
                    (switch-todo-done-state 2)
                    (get-todo-by-id 2)
                    (:done)))
           (is= (-> (create-app-state :todo-list [])
                    (switch-todo-done-state 1)
                    (get-todo-list))
                []))}
  [state id]
  (->> (get-todo-list state)
       (map (fn [todo-item]
              (if (= id (:id todo-item))
                (assoc todo-item :done (not (:done todo-item)))
                todo-item)))
       (set-todo-list state)))

(defn is-todo-done?
  {:test (fn []
           (is (-> (create-app-state :todo-list [{:id 1 :title "title" :description "description" :done false}])
                   (is-todo-done? 1)
                   (not)))
           (is (-> (create-app-state :todo-list [{:id 1 :title "title" :description "description" :done true}])
                   (is-todo-done? 1)))
           (is (-> (create-app-state :todo-list [{:id 1 :title "title" :description "description" :done false}])
                   (is-todo-done? 5)
                   (not)))
           (is (-> (create-app-state :todo-list [{:id 341982, :title "", :description "", :done false}
                                                 {:id 472151, :title "", :description "", :done false}
                                                 {:id 275142, :title "", :description "", :done false}
                                                 {:id 789921, :title "", :description "", :done false}
                                                 {:id 129069, :title "", :description "", :done false}
                                                 {:id 259341, :title "", :description "dwq-ewqeq", :done false}
                                                 {:id 699670, :title "", :description "dwq-ewqe", :done false} 
                                                 {:id 898693, :title "asdewqewq", :description "", :done false}
                                                 {:id 596100, :title "", :description "", :done false}
                                                 {:id 935761, :title "asd", :description "dwq-", :done false}
                                                 {:id 651602, :title "21312", :description "321", :done false}])
                   (is-todo-done? 341982)
                   (not)))
           
           )}
  [state id]
  (-> (get-todo-by-id state id)
      (:done)))