(ns app.frontend.core
  (:require [reagent.dom :as rdom]
            [app.frontend.view :as home-view :refer [app]]))
(enable-console-print!)
(defn ^:dev/after-load start
  []
  (rdom/render [app]
                            (.getElementById js/document "app")))

(defn ^:export main
  []
  (start))