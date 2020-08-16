(ns app.frontend.sidenav
  (:require [app.frontend.state :refer [app-state-atom]]
            ["semantic-ui-react" :refer (Button Header Icon Modal)]
            [app.frontend.events :refer [!close-sidenav
                                         !open-sidenav]]))

;; example of semantic ui react
(defn sui
  []
  [:div
   [:> Button {:primary true} "Primary"]
   [:> Button {:secondary true} "Secondary"]])

(defn is-direction-right?
  [touch-state]
  (if (> (:current-x touch-state) (:start-x touch-state))
    true
    false))

(defn get-sidenav-ratio
  [touch-state max-width]
  (let [start-x (:start-x touch-state)
        current-x (:current-x touch-state)
        swipe-distance (.abs js/Math (- start-x current-x))
        ratio (/ swipe-distance max-width)]
    (println ratio)
    (if (> ratio 1)
      1
      ratio)))

(defn is-sidenav-open?
  [touch-state max-width]
  (cond 
    (:is-sidenav-open @app-state-atom)
    true
    
    (> (get-sidenav-ratio touch-state max-width) 0.5)
    true
    
    :else
    false))

(defn get-sidenav-width
  [touch-state max-width]
  (let [ratio (get-sidenav-ratio touch-state max-width)
        is-touch-active? (:touch-active touch-state)]
    (println is-touch-active?)
    (cond
      is-touch-active?
      (str
       (* ratio 200) "px")

      (and (is-sidenav-open? touch-state max-width) (not is-touch-active?))
      "200px"

      :else
      "0px")))

(defn sidenav-component
  [app-state]
  (let [links ["About" "Services" "Clients" "Contact"]]
    [:div {:id "sidenav"
           :class "sidenav"
           :style {:height "100%"
                   :width (get-sidenav-width (:touch-state app-state) (:sidenav-width app-state))
                   :position "fixed"
                   :z-index 1
                   :top 0
                   :left 0
                   :background-color "#555"
                   :overflow-x "hidden"
                   :padding-top "10px"
                   :transition "0.5s"}}
     [:a {:href "#"
          :onClick (fn [] (!close-sidenav))
          :class "sidenav-link"
          :style {:padding "8px 8px 8px 32px"
                  :text-decoration "none"
                  :font-size "18px"
                  :color "white"
                  :display "block"
                  :transition "0.3s"}}
      "Close X"]
     (map (fn [link-title]
            [:a {:href "#"
                 :key link-title
                 :class "sidenav-link"
                 :style {:padding "8px 8px 8px 32px"
                         :text-decoration "none"
                         :font-size "18px"
                         :color "white"
                         :display "block"
                         :transition "0.3s"}}
             link-title])
          links)]))
