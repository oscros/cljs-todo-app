(ns app.frontend.confirmDeletionDialog
  (:require [reagent.core :as r]
            [app.frontend.events :refer [close-add-modal!
                                         remove-todo!]]
            ["@material-ui/core/TextField" :default TextField]
            ["@material-ui/core/Button" :default Button]
            ["@material-ui/core/Dialog" :default Dialog]
            ["@material-ui/core/DialogTitle" :default DialogTitle]
            ["@material-ui/core/DialogContentText" :default DialogContentText]
            ["@material-ui/core/DialogActions" :default DialogActions]
            ["@material-ui/core/DialogContent" :default DialogContent]
            ["@material-ui/core/Slide" :default Slide]))

;; state and open/close handled in the component itself

(defonce deletion-dialog-atom (r/atom {:item-id nil :dialog-open false}))

(defn open-deletion-dialog
  [item-id]
  (reset! deletion-dialog-atom {:item-id item-id :dialog-open true}))

(defn close-deletion-dialog
  []
  (reset! deletion-dialog-atom {:item-id nil :dialog-open false}))

(defn confirm-deletion-dialog
  []
  [:> Dialog {:fullscreen "true" :open (:dialog-open @deletion-dialog-atom) :onClose (fn []
                                                                                       (close-add-modal!))
              :keepMounted true
              :TransitionComponent Slide
              :Transition-props     {:direction "up"}}
   [:> DialogTitle "Please Confirm Deletion"]
   [:> DialogContent
    [:> DialogContentText "Are you sure you want to delete this todo"]]
   [:> DialogActions
    [:> Button {:color "secondary" :onClick (fn []
                                              (close-deletion-dialog))} "No"]
    [:> Button {:color "primary" :onClick (fn []
                                            (remove-todo! (:item-id @deletion-dialog-atom))
                                            (close-deletion-dialog))} "Yes"]]])