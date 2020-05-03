(ns app.frontend.events
  (:require [app.frontend.state :refer [app-state-atom]]))

(defn !set-active-tab
  [tab-key]
  (swap! app-state-atom assoc-in [:tabs :current-tab] tab-key))

(defn !open-sidenav
  []
  (swap! app-state-atom assoc :is-sidenav-open true))

(defn !close-sidenav
  []
  (swap! app-state-atom assoc :is-sidenav-open false))

(defn touch-state-change!
  [touch-state]
  (swap! app-state-atom assoc :touch-state touch-state))
