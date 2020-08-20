(ns app.frontend.state
  (:require [reagent.core :refer [atom]]
            [app.logic.core :refer [create-app-state]]))

(defonce app-state-atom (atom (create-app-state)))




(comment 
  (reset! app-state-atom nil)
  (deref app-state-atom))
