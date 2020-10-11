(ns app.frontend.shared.state
  (:require [reagent.core :refer [atom]]
            [app.logic.core :refer [create-app-state]]))

(defonce app-state-atom (atom {:view-state {:is-drawer-open false
                                            :is-todo-dialog-open false}
                               :core-state (create-app-state)}))




(comment 
  (reset! app-state-atom nil)
  (deref app-state-atom))
