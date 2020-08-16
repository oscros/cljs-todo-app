(ns app.frontend.view
  (:require [reagent.core :as r]
<<<<<<< HEAD
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
=======
            [app.frontend.state :refer [app-state-atom]]
            [app.frontend.events :refer [!set-active-tab
                                         touch-state-change!]]
            [app.frontend.sidenav :refer [sidenav-component]]))

(defn get-tab-css-class
  [tab-id app-state]
  (let [active-tab (get-in app-state [:tabs :current-tab])]
    (cond
      (and (= "home-tab" tab-id) (= active-tab :home))
      "tablink active"

      (and (= "news-tab" tab-id) (= active-tab :news))
      "tablink active"

      (and (= "contact-tab" tab-id) (= active-tab :contact))
      "tablink active"

      (and (= "about-tab" tab-id) (= active-tab :about))
      "tablink active"

      :else
      "tablink")))

(defn create-tab
  [app-state tab-id tab-key tab-title]
  [:button {:id tab-id :key tab-key :class (get-tab-css-class tab-id app-state) :onClick (fn [] (!set-active-tab tab-key))} tab-title])

(defn tabs
  [app-state]
  [:div {:class "tabbar"}
   (map (fn [tab]
          (create-tab app-state (:id tab) (:key tab) (:title tab)))
        (get-in app-state [:tabs :all-tabs]))])



(defn touch-start
  [touch-event]
  (let [touch-object (.item (.. touch-event -touches) 0)]
    (touch-state-change! {:start-x (.-clientX touch-object)
                          :start-y (.-clientY touch-object)
                          :current-x (.-clientX touch-object)
                          :touch-active true
                          :end-x (.-clientX touch-object)})))

(defn touch-move
  [touch-event]
  (let [touch-object (.item (.. touch-event -touches) 0)
        x-position (.-clientX touch-object)
        y-position (.-clientY touch-object)]
    (when (and (> x-position (get-in @app-state-atom [:touch-state :start-x])) (> (.abs js/Math (- (get-in @app-state-atom [:touch-state :start-x]) x-position)) 10)) ; swipe only activates when diff > 10
      (touch-state-change! {:start-x (get-in @app-state-atom [:touch-state :start-x])
                            :start-y (get-in @app-state-atom [:touch-state :start-y])
                            :current-x x-position
                            :touch-active true
                            :end-x x-position}))))

(defn touch-end
  [touch-event]
  (touch-state-change! {:start-x (get-in @app-state-atom [:touch-state :start-x])
                        :start-y (get-in @app-state-atom [:touch-state :start-y])
                        :current-x (get-in @app-state-atom [:touch-state :current-x])
                        :touch-active false
                        :end-x (get-in @app-state-atom [:touch-state :end-x])}))

(defn tab-content
  [app-state]
  [:div
   (condp = (get-in app-state [:tabs :current-tab])

     :home
     [:div
      [:div {:id "Home"
             :class "tabcontent"
             :on-touch-start (fn [e] (touch-start e))
             :on-touch-end (fn [e] (touch-end e))
             :on-touch-move (fn [e] (touch-move e))}
       [:h3 "Home"]
       [:p "Home is where the heart is"]]]

     :news
     [:div {:id "News" :class "tabcontent"}
      [:h3 "News"]
      [:p "News is where the heart is"]]

     :contact
     [:div {:id "Contact" :class "tabcontent"}
      [:h3 "Contact"]
      [:p "Contact is where the heart is"]]

     :about
     [:div {:id "About" :class "tabcontent"}
      [:h3 "About"]
      [:p "About is where the heart is"]])])

(defn app
  []
  [:div
   [sidenav-component @app-state-atom]
   [tab-content @app-state-atom]
   [tabs @app-state-atom]])
>>>>>>> 02e7c23786992af391d26bbde0932451408e93a2

(comment
  (deref app-state-atom)
  (reset! app-state-atom nil))