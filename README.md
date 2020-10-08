# Built With
Leiningen 
Clojure
ClojureScript
nodeJS
Material UI
Calva (vscode)





# My Notes 

# How to connect calva 
https://www.youtube.com/watch?v=c9I1B1VZies
1. npx shadow-cljs watch app
2. press ctrl+alt+c twice
3. choose :app build

to run  tests: ctrl+alt+c t

# State management thoughts
* Split upp global ratom into view-state and logic-state (future backend) and keep all state there. 
* ~~Only have state in a global ratom~~
* ~~have component specific state (only transient state, i.e temporary state which will disappear) in each component and global/logic state in globar ratom~~

## TODO
1. fix state management above
2. organize files according to features or views?
3. serviceworker [youtube example](https://www.youtube.com/watch?v=atUdVSuNRjA) [github example](https://github.com/surabayajs/calculator-pwa-clojurescript/blob/master/src/calculator_app/core.cljs)  
