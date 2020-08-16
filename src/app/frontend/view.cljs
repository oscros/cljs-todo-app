(ns app.frontend.view
  (:require [reagent.core :as r]
            [app.frontend.events :refer [change-content!
                                         open-drawer!
                                         close-drawer!
                                         open-add-modal!
                                         close-add-modal!]]
            [app.frontend.state :refer [app-state-atom]]
            ["@material-ui/core/styles" :rename {ThemeProvider MuiThemeProvider}]
            ["@material-ui/core/styles" :refer [createMuiTheme makeStyles]]
            ["@material-ui/core/colors" :as mui-colors]
            ["@material-ui/core/CssBaseline" :default CssBaseline]
            ["@material-ui/core/Typography" :default Typography]
            ["@material-ui/core/AppBar" :default AppBar]
            ["@material-ui/core/SwipeableDrawer" :default SwipeableDrawer]
            ["@material-ui/core/List" :default List]
            ["@material-ui/core/ListItem" :default ListItem]
            ["@material-ui/core/ListItemIcon" :default ListItemIcon]
            ["@material-ui/core/ListItemText" :default ListItemText]
            ["@material-ui/core/Toolbar" :default ToolBar]
            ["@material-ui/core/IconButton" :default IconButton];
            ["@material-ui/core/Button" :default Button]
            ["@material-ui/core/Fab" :default Fab]
            ["@material-ui/core/Dialog" :default Dialog]
            ["@material-ui/core/Slide" :default Slide]
            
            ["@material-ui/icons/List" :default ListIcon]
            ["@material-ui/icons/Info" :default InfoIcon]
            ["@material-ui/icons/Add" :default AddIcon]))
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
  [content]
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
                           :open (:is-drawer-open @app-state-atom)
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
    [:h1 "This is my first, HELLO!"]
    [:> Button {:color :primary :variant :contained :onClick (fn []
                                                               (change-content! "TESTSTES"))}  "button"]
    [:> Fab {:color :primary :aria-label "add" :style {:position "absolute"
                                                        :bottom "1rem"
                                                        :right "1rem"}
             :onClick (fn []
                        (open-add-modal!))}
     [:> AddIcon]]
    [:> Dialog {:fullscreen "true" :open (:is-modal-open @app-state-atom) :onClose (fn []
                                                                                     (close-add-modal!))
                :TransitionComponent [:> Slide {:direction "up"}]}
     ]]])


(defn app-component
  []
  [:div 
   [frame @app-state-atom]])

(comment
  (deref app-state-atom)
  (reset! app-state-atom nil))