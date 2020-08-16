(ns app.frontend.main
  (:require [reagent.core :as reagent]
            [reagent.dom :as rdom]
            [app.frontend.view :refer [app-component]]
            [app.frontend.events :refer [set-active-tab
                                         change-content]]))

(enable-console-print!)
(defonce app-state-atom (atom nil))

(defn handle-event!
  [{name :name data :data}]
  (condp = name
    :set-active-tab
    (reset! app-state-atom
            (set-active-tab @app-state-atom data))

    :change-content
    (reset! app-state-atom
            (change-content @app-state-atom data))))

(defn render
  [app-state]
  (rdom/render [app-component app-state handle-event!]
                            (js/document.getElementById "app")))



(when (nil? (deref app-state-atom))

  (add-watch app-state-atom
             :change
             (fn [_ _ old-state new-state]
               (when (not= old-state new-state)
                 (render new-state))))
  (reset! app-state-atom {:count 0
                          :is-sidenav-open false
                          :sidenav-width 200
                          :touch-state {:start-x 0
                                        :start-y 0
                                        :current-x 0
                                        :touch-active false
                                        :end-x 0}
                          :index 0
                          :tabs {:current-tab :home
                                 :class "tablink active"
                                 :all-tabs [{:key :home :id "home-tab" :class "tablink" :title "test"}
                                            {:key :news :id "news-tab" :class "tablink" :title "News"}
                                            {:key :contact :id "contact-tab" :class "tablink" :title "Contact"}
                                            {:key :about :id "about-tab" :class "tablink" :title "About"}]}}))

(defn on-js-reload []
  (render (deref app-state-atom)))

(comment (deref app-state-atom)
         (reset! app-state-atom nil)
         (js/alert "testst"))