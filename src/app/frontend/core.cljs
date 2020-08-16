(ns app.frontend.core
  (:require [reagent.dom :as rdom]
<<<<<<< HEAD
            [app.frontend.view :refer [app-component]]))
(enable-console-print!)
(defn ^:dev/after-load start
  []
  (rdom/render [app-component ]
=======
            [app.frontend.view :as home-view :refer [app]]))
(enable-console-print!)
(defn ^:dev/after-load start
  []
  (rdom/render [app]
>>>>>>> 02e7c23786992af391d26bbde0932451408e93a2
                            (.getElementById js/document "app")))

(defn ^:export main
  []
  (start))