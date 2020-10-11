
(ns app.frontend.todoList
  (:require [app.frontend.shared.events :refer [switch-todo-done-state!
                                         remove-todo!]]
            [app.logic.core :refer [is-todo-done?]]
            [app.frontend.confirmDeletionDialog :refer [open-deletion-dialog]]
            ["@material-ui/core/TextField" :default TextField]
            ["@material-ui/core/List" :default List]
            ["@material-ui/core/ListItem" :default ListItem]
            ["@material-ui/core/ListItemIcon" :default ListItemIcon]
            ["@material-ui/core/ListItemText" :default ListItemText]
            ["@material-ui/core/ListItemSecondaryAction" :default ListItemSecondaryAction]
            ["@material-ui/core/IconButton" :default IconButton];
            ["@material-ui/core/Button" :default Button]
            ["@material-ui/core/Checkbox" :default Checkbox]
            ["@material-ui/icons/Delete" :default DeleteIcon]))

(defn todo-list
  [app-state]
  [:> List
   (map (fn [item]
          [:> ListItem {:button true :key (:id item)}
           [:> ListItemIcon
            [:> Checkbox {:edge "start" :checked (is-todo-done? (:core-state app-state) (:id item)) :onClick (fn []                                                       
                                                                         (switch-todo-done-state! (:id item)))}]]
           [:> ListItemText (if (is-todo-done? (:core-state app-state) (:id item))
                              {:style {:text-decoration "line-through"}}
                              {})
            (:title item)]
           [:> ListItemSecondaryAction
            [:> IconButton {:edge "end" :aria-label "delete" :onClick (fn []
                                                                        (open-deletion-dialog (:id item)))}
             [:> DeleteIcon]]]])
        (:todo-list (:core-state app-state)))])
