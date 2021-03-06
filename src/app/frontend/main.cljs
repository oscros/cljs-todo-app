(ns app.frontend.main
  (:require [reagent.dom :as rdom]
            [app.frontend.view :refer [app-component]]))
(enable-console-print!)
(defn ^:dev/after-load start
  []
  (rdom/render [app-component ]
                            (.getElementById js/document "app")))

(defn- make-progressive! []
  (when js/navigator.serviceWorker
    (.register js/navigator.serviceWorker "/service-worker.js")))

(defn ^:export main
  []
  (make-progressive!)
  (start))