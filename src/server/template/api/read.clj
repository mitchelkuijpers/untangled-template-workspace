(ns template.api.read
  (:require
    ;[untangled.datomic.protocols :as udb]
    [template.api.mutations :as m]
    [taoensso.timbre :as timbre]))

(timbre/info "Loading API definitions for template.api.read")


(defn api-read [{:keys [query request] :as env} disp-key params]
  ;(let [connection (udb/get-connection survey-database)])
  (case disp-key
    :logged-in? {:value @m/logged-in?}
    :hello-world {:value 42}
    :users {:value [{:db/id 1 :user/name "mitchel"} {:db/id 2 :user/name "tony"}]}
    :current-user {:value {:id 42 :name "Tony Kay"}}
    (throw (ex-info "Invalid request" {:query query :key disp-key}))))
