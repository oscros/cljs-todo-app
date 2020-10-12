(ns app.frontend.main
  (:require [reagent.dom :as rdom]
            [app.frontend.view :refer [app-component]]))
(enable-console-print!)
(defn ^:dev/after-load start
  []
  (rdom/render [app-component ]
                            (.getElementById js/document "app")))

(defn ^:export main
  []

  (try
    (-> (. js/navigator.serviceWorker (register "/service-worker.js"))
        (.then (fn [] (js/console.log "service worker registered"))))

    (catch js/Object err (js/console.error "Failed to register service worker" err)))
  (start))