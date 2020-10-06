
(ns app.frontend.todoList
  (:require
   [app.frontend.events :refer [switch-todo-done-state!
                                remove-todo!]]
   [app.logic.core :refer [is-todo-done?]]
   [app.frontend.state :refer [app-state-atom]]
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
;

(defn todo-list
  [app-state]
  [:> List
   (map (fn [item]
          [:> ListItem {:button true :key (:id item)}
           [:> ListItemIcon
            [:> Checkbox {:edge "start" :checked (is-todo-done? app-state (:id item)) :onClick (fn []                                                       
                                                                         (switch-todo-done-state! (:id item)))}]]
           [:> ListItemText (:title item)]
           [:> ListItemSecondaryAction
            [:> IconButton {:edge "end" :aria-label "delete" :onClick (fn []
                                                                        (remove-todo! (:id item)))}
             [:> DeleteIcon]]]])
        (:todo-list app-state))])
