(ns app.frontend.aboutPage)


(defn about-page
  []
  [:div {:style {:text-align "center"}}
   [:h2 "About this App"]
   [:p "This app was created for educational purposes by Oscar Rosquist"]
   [:p "It was built with ClojureScript and Material UI"]])