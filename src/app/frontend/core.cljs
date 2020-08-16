(ns app.frontend.core
  (:require [reagent.dom :as rdom]
            [app.frontend.view :refer [app-component]]))
(enable-console-print!)
(defn ^:dev/after-load start
  []
  (rdom/render [app-component ]
                            (.getElementById js/document "app")))

(defn ^:export main
  []
  (start))