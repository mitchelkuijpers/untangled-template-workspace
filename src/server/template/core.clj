(ns template.core
  (:require
    [com.stuartsierra.component :as component]
    [template.system :as sys]
    [untangled.server.core :as c]
    [untangled.server.impl.components.config :refer [load-config]]
    [taoensso.timbre :as timbre])
  (:gen-class))

(def console (System/console))
(defn exit [exit-code]
  (System/exit exit-code))
(defn exit-if-headly [exit-code]
  (if console (exit exit-code)))

(def config-path "/usr/local/etc/template.edn")
(def production-config-component (c/new-config config-path))

(defn -main [& args]
  (let [system (sys/make-system config-path)]
    (component/start system)))
