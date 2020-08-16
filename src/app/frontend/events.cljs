(ns app.frontend.events
  (:require [app.frontend.state :refer [app-state-atom]]))

<<<<<<< HEAD
(defn set-active-tab
  [app-state tab-key]
  (assoc-in app-state [:tabs :current-tab] tab-key))

(defn change-content!
  [content]
  (do (print "changing content"))
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

;; (defn touch-state-change!
;;   [touch-state]
;;   (swap! app-state-atom assoc :touch-state touch-state))
=======
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
>>>>>>> 02e7c23786992af391d26bbde0932451408e93a2
