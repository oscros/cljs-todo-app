(ns app.frontend.createTodoDialog
  (:require [reagent.core :as r]
            [app.frontend.events :refer [close-add-modal!
                                         create-to-do!]]
            ["@material-ui/core/TextField" :default TextField]
            ["@material-ui/core/Button" :default Button]
            ["@material-ui/core/Dialog" :default Dialog]
            ["@material-ui/core/DialogTitle" :default DialogTitle]
            ["@material-ui/core/DialogContentText" :default DialogContentText]
            ["@material-ui/core/DialogActions" :default DialogActions]
            ["@material-ui/core/DialogContent" :default DialogContent]
            ["@material-ui/core/Slide" :default Slide]))

;; state handled in the component itself

(defonce dialog-input-atom (r/atom {:title "Title"
                                    :description "Description"}))

(defn create-todo-dialog
  [state]
  [:> Dialog {:fullscreen "true" :open (:is-modal-open state) :onClose (fn []
                                                                         (close-add-modal!))
              :keepMounted true
              :TransitionComponent Slide
              :Transition-props     {:direction "up"}}
   [:> DialogTitle "Create new To Do"]
   [:> DialogContent
    [:> DialogContentText "Please enter a title and description for the new to do"]
    [:> TextField {:margin "dense" :id "title-field" :label "Title" :required true :fullWidth true :onChange (fn [event]
                                                                                                               (swap! dialog-input-atom assoc :title (.-value (.-target event))))}]
    [:> TextField {:margin "dense" :id "description-field" :label "Description" :required true :fullWidth true :onChange (fn [event]
                                                                                                                           (swap! dialog-input-atom assoc :description (.-value (.-target event))))}]]
   [:> DialogActions
    [:> Button {:color "secondary" :onClick (fn []
                                              (close-add-modal!))} "cancel"]
    [:> Button {:color "primary" :onClick (fn []
                                            (create-to-do! (:title @dialog-input-atom) (:description @dialog-input-atom))
                                            (close-add-modal!))} "Create"]]])