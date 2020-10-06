(ns app.frontend.events
  (:require [app.frontend.state :refer [app-state-atom]]
            [app.logic.core :refer [add-new-to-do
                                    switch-todo-done-state
                                    remove-todo]]))

(defn set-active-tab
  [app-state tab-key]
  (assoc-in app-state [:tabs :current-tab] tab-key))

(defn change-content!
  [content]
  (swap! app-state-atom assoc :content content))

(defn open-drawer!
  []
  (swap! app-state-atom assoc :is-drawer-open true))

(defn close-drawer!
  []
  (swap! app-state-atom assoc :is-drawer-open false))

(defn open-add-modal!
  []
  (swap! app-state-atom assoc :is-modal-open true))

(defn close-add-modal!
  []
  (swap! app-state-atom assoc :is-modal-open false))

(defn create-to-do!
  [title description]
  (print "create new todo")
  (print title description)
  (swap! app-state-atom add-new-to-do title description))

(defn switch-todo-done-state!
  [id]
  ;; (println "set todo done")
  ;; (println id)
  (swap! app-state-atom switch-todo-done-state id))

(defn remove-todo!
  [id]
  (swap! app-state-atom remove-todo id))

;; (defn touch-state-change!
;;   [touch-state]
;;   (swap! app-state-atom assoc :touch-state touch-state))
