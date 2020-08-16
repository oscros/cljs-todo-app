(ns app.frontend.state
  (:require [reagent.core :refer [atom]]))

(defonce app-state-atom (atom {:count 0
                               :is-drawer-open false
                               :is-modal-open false
                               :sidenav-width 200
                               :touch-state {:start-x 0
                                             :start-y 0
                                             :current-x 0
                                             :touch-active false
                                             :end-x 0}
                               :index 0
                               :tabs {:current-tab :home
                                      :class "tablink active"
                                      :all-tabs [{:key :home :id "home-tab" :class "tablink" :title "Home"}
                                                 {:key :news :id "news-tab" :class "tablink" :title "News"}
                                                 {:key :contact :id "contact-tab" :class "tablink" :title "Contact"}
                                                 {:key :about :id "about-tab" :class "tablink" :title "About"}]}}))




(comment 
  (reset! app-state-atom nil)
  (deref app-state-atom))