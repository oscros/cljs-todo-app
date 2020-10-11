(ns app.frontend.createTodoDialog
  (:require [reagent.core :as r]
            [app.frontend.shared.events :refer [close-add-modal!
                                         create-to-do!]]
            [app.frontend.shared.subscriptions :refer [is-todo-dialog-open?]]
            ["@material-ui/core/TextField" :default TextField]
            ["@material-ui/core/Button" :default Button]
            ["@material-ui/core/Dialog" :default Dialog]
            ["@material-ui/core/DialogTitle" :default DialogTitle]
            ["@material-ui/core/DialogContentText" :default DialogContentText]
            ["@material-ui/core/DialogActions" :default DialogActions]
            ["@material-ui/core/DialogContent" :default DialogContent]
            ["@material-ui/core/Slide" :default Slide]))

;; state handled in the component itself
;; 
(def input-defaults {:title "" :description "" :error false})

(def dialog-input-atom (r/atom input-defaults))

(defn reset-input-fields
  []
  (reset! dialog-input-atom input-defaults))

(defn is-title-valid?
  []
  (> (count (get @dialog-input-atom :title)) 0))

(defn clear-validation-error
  []
  (swap! dialog-input-atom assoc :error false))

(defn show-validation-error
  []
  (swap! dialog-input-atom assoc :error true))

(defn create-and-close
  []
  (create-to-do! (:title @dialog-input-atom) (:description @dialog-input-atom))
  (reset-input-fields)
  (close-add-modal!))

(defn title-change
  [event]
  (swap! dialog-input-atom assoc :title (.-value (.-target event)))
  (clear-validation-error))

(defn on-description-change
  [event]
  (swap! dialog-input-atom assoc :description (.-value (.-target event))))

(defn create-todo-dialog
  [state]
  [:> Dialog {:fullscreen "true" :open (is-todo-dialog-open? state) :onClose (fn []
                                                                         (close-add-modal!))
              :keepMounted false
              :TransitionComponent Slide
              :Transition-props     {:direction "up"}}
   [:> DialogTitle "Create new To Do"]
   [:> DialogContent
    [:> DialogContentText "Please enter a title and description for the new to do"]
    [:> TextField {:margin "dense" :id "title-field" :error (:error @dialog-input-atom) :helperText "Title cannot be empty" :label "Title" :required true :fullWidth true :onChange title-change}]
    [:> TextField {:margin "dense" :id "description-field" :label "Description" :required true :fullWidth true :onChange on-description-change}]]
   [:> DialogActions
    [:> Button {:color "secondary" :onClick (fn []
                                              (reset-input-fields)
                                              (close-add-modal!))} "cancel"]
    [:> Button {:color "primary" :onClick (fn []
                                            (if (not (is-title-valid?))
                                              (show-validation-error)
                                              (create-and-close)))} "Create"]]])