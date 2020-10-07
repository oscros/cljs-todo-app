(ns app.frontend.view
  (:require [reagent.core :as r]
            [app.frontend.events :refer [change-content!
                                         open-drawer!
                                         close-drawer!
                                         open-add-modal!
                                         close-add-modal!
                                         create-to-do!
                                         switch-todo-done-state!
                                         remove-todo!]]
            [app.logic.core :refer [is-todo-done?]]
            [app.frontend.state :refer [app-state-atom]]
            [app.frontend.createTodoDialog :refer [create-todo-dialog]]
            [app.frontend.todoList :refer [todo-list]]
            [app.frontend.confirmDeletionDialog :refer [confirm-deletion-dialog]]
            ["@material-ui/core/styles" :rename {ThemeProvider MuiThemeProvider}]
            ["@material-ui/core/styles" :refer [createMuiTheme makeStyles]]
            ["@material-ui/core/colors" :as mui-colors]
            ["@material-ui/core/CssBaseline" :default CssBaseline]
            ["@material-ui/core/Typography" :default Typography]
            ["@material-ui/core/AppBar" :default AppBar]
            ["@material-ui/core/TextField" :default TextField]
            ["@material-ui/core/SwipeableDrawer" :default SwipeableDrawer]
            ["@material-ui/core/List" :default List]
            ["@material-ui/core/ListItem" :default ListItem]
            ["@material-ui/core/ListItemIcon" :default ListItemIcon]
            ["@material-ui/core/ListItemText" :default ListItemText]
            ["@material-ui/core/ListItemSecondaryAction" :default ListItemSecondaryAction]
            ["@material-ui/core/Toolbar" :default ToolBar]
            ["@material-ui/core/IconButton" :default IconButton];
            ["@material-ui/core/Button" :default Button]
            ["@material-ui/core/Checkbox" :default Checkbox]
            ["@material-ui/core/Fab" :default Fab]
            ["@material-ui/icons/List" :default ListIcon]
            ["@material-ui/icons/Info" :default InfoIcon]
            ["@material-ui/icons/Add" :default AddIcon]
            ["@material-ui/icons/Delete" :default DeleteIcon]))
;

(defn custom-theme []
  (createMuiTheme 
    (clj->js
      {:palette
       {:type       "light"
        :primary    (.-blue mui-colors)
        :secondary  (.-red mui-colors)}
       :typography
       {:useNextVariants true}})))
            ;;; !!! :font-family "Roboto, sans-serif"}})))
            ;:fontSize 12}})))


(defn frame
  [state]
  [:> MuiThemeProvider
   {:theme (custom-theme)}
   [:<> ;div {:class :root}
    [:> CssBaseline]
    [:> AppBar
     {:position "static"
      :className "appBar"}   ;, this.state.open && classes.appBarShift}
     [:> ToolBar
      [:> Typography
       {:variant "h6" :color "inherit" :no-wrap true}
       "To Do List"]
      [:> SwipeableDrawer {:anchor "left"
                           :open (:is-drawer-open state)
                           :onClose (fn []
                                      (close-drawer!))
                           :onOpen (fn []
                                     (open-drawer!))}
       [:> List
        (map (fn [item]
               [:> ListItem {:button true :key (:key item)}
                [:> ListItemIcon
                 [:> (:icon item)]]
                [:> ListItemText (:title item)]])
             [{:title "To Do List" :icon ListIcon :key :todo}
              {:title "About this App" :icon InfoIcon :key :info}])]]]]
    (todo-list state)
    [:> Fab {:color :primary :aria-label "add" :style {:position "absolute"
                                                       :bottom "1rem"
                                                       :right "1rem"}
             :onClick (fn []
                        (open-add-modal!))}
     [:> AddIcon]]
    (create-todo-dialog state)
    (confirm-deletion-dialog)]])


(defn app-component
  []
  [:div 
   [frame @app-state-atom]])

(comment
  (deref app-state-atom)
  (reset! app-state-atom nil))