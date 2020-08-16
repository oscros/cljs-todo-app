(ns app.frontend.events
  (:require [app.frontend.state :refer [app-state-atom]]))

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
