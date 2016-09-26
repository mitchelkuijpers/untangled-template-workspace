(ns template.ui.main
  (:require [om.next :as om :refer-macros [defui]]
            [untangled.client.core :as u]
            [om.dom :as dom]
            [om-css.core :as css :refer-macros [localize-classnames]]
            [untangled.client.mutations :as m]
            [untangled.client.data-fetch :as f]))

(defmethod m/mutate 'users/server-down [{:keys [state]} k p]
  {:action (fn [] (swap! state assoc :server-down true))})

(defui ^:once User
  static om/IQuery
  (query [_]
    [:db/id :user/name :ui/fetch-state])
  static om/Ident
  (ident [_ props]
    [:user/by-id (:db/id props)]))

(defui ^:once MainPage
  static u/InitialAppState
  (initial-state
      [this params] {:id :main})
  static om/IQuery
  (query [this]
    [:id
     [:ui/loading-data '_]
     [:current-user '_]
     {[:users '_] (om/get-query User)}])
  static css/CSS
  (css [this]
    [[(css/local-kw MainPage :x)]])
  static om/Ident
  (ident [this props]
    [:main :page])
  Object
  (componentDidMount [this]
    (f/load-data this
                 [{:users (om/get-query User)}]
                 :marker false

                 ;; When you remove the params it will actually have a :ui/fetch-state
                 :params {:users {:foo "bar"}}
                 :refresh [:users]))
  (render [this]
    (localize-classnames MainPage
                         (let [{:keys [current-user users] :as props} (om/props this)]
                           (.log js/console "UI FETCH STATE ---- " (get-in props [:users :ui/fetch-state]))
                           (dom/div #js {:class :form}
                             "MAIN -- PAGE"
                             (dom/button #js {:onClick #(f/load-data this (om/focus-query (om/get-query this) [:users]))}
                               "Load users"))))))

(def ui-main (om/factory MainPage))
