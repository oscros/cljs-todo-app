(ns app.frontend.view
  (:require [reagent.core :as r]
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

(comment
  (deref app-state-atom)
  (reset! app-state-atom nil))