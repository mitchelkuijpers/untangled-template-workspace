(ns template.core
  (:require [om.next :as om]
            [untangled.client.core :as uc]
            [untangled.client.data-fetch :as f]
            template.state.mutations))

(defonce app
         (atom (uc/new-untangled-client
                :started-callback (fn [{:keys [reconciler] :as app}]
                                    (.log js/console app)
                                     (f/load-data reconciler
                                                  [:logged-in? :current-user]
                                                  :post-mutation 'login/login-complete)
                                     ;;TODO: initial load of data
                                     ))))
