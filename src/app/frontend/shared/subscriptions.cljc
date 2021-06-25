(ns app.frontend.shared.subscriptions)

(defn is-drawer-open?
  [state]
  (get-in state [:view-state :is-drawer-open]))

(defn is-todo-dialog-open?
  [state]
  (get-in state [:view-state :is-todo-dialog-open]))

(defn get-current-view
  [state]
  (get-in state [:view-state :view]))
