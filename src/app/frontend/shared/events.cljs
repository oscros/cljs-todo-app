(ns app.frontend.shared.events
  (:require [app.frontend.shared.state :refer [app-state-atom]]
            [app.logic.core :refer [add-new-to-do
                                    switch-todo-done-state
                                    remove-todo]]))


(defn open-drawer!
  []
  (swap! app-state-atom assoc-in [:view-state :is-drawer-open] true))

(defn close-drawer!
  []
  (swap! app-state-atom assoc-in [:view-state :is-drawer-open] false))

(defn open-add-modal!
  []
  (swap! app-state-atom assoc-in [:view-state :is-todo-dialog-open] true))

(defn close-add-modal!
  []
  (swap! app-state-atom assoc-in [:view-state :is-todo-dialog-open] false))

(defn create-to-do!
  [title description]
  (->> (add-new-to-do (:core-state @app-state-atom) title description)
       (swap! app-state-atom assoc :core-state)))

(defn switch-todo-done-state!
  [id]
  (->> (switch-todo-done-state (:core-state @app-state-atom) id)
       (swap! app-state-atom assoc :core-state)))

(defn remove-todo!
  [id]
  (->> (remove-todo (:core-state @app-state-atom) id)
       (swap! app-state-atom assoc :core-state)))

(defn navigate-to!
  [destination]
  (print "navigating")
  (swap! app-state-atom assoc-in [:view-state :view] destination))

;; (defn touch-state-change!
;;   [touch-state]
;;   (swap! app-state-atom assoc :touch-state touch-state))
