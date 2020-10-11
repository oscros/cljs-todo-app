
# Todo app
Small practice app for todo handling written in Clojure and ClojureScript. 


### Built With
* Leiningen 
* Clojure
* ClojureScript
* Reagent
* nodeJS
* Material UI
* Calva (vscode)





# My Notes 

### How to connect calva 
https://www.youtube.com/watch?v=c9I1B1VZies
1. npx shadow-cljs watch app
2. press ctrl+alt+c twice
3. choose :app build

to run  tests: ctrl+alt+c t

### State management thoughts
* Split upp global ratom into view-state and logic-state (future backend) and keep all state there. 
* have component specific state (only transient state, i.e temporary state which will disappear) in each component that needs it (keeping track of input etc) and global/logic state in global ratom


### TODO/Future ideas
1. Fill in about page and fix drawer icon
2. make PWA serviceworker [youtube example](https://www.youtube.com/watch?v=atUdVSuNRjA) [github example](https://github.com/surabayajs/calculator-pwa-clojurescript/blob/master/src/calculator_app/core.cljs)  
3. hook up to firebase with login functionality and persistant data storage
