(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))

;; Задание 2-1
(defn task21 [files]
  (filter (fn [a] (not (get a :directory))) files)
  )

;; (metrics-server.api.files/task21 (metrics-server.api.files/get-files))

;; Задание 2-2
(defn task22 [files]
  (filter (fn [a] (not (get a :executable))) files)
  )

;; (metrics-server.api.files/task22 (metrics-server.api.files/get-files))

;; Задание 2-3
(defn task23 [files]
  (map (fn [file]
         {
          :name (clojure.string/replace (get file :name) #".conf" ".cfg")
          :size (get file :size)
          :changed (get file :changed)
          :directory (get file :directory)
          :executable (get file :executable)
          }
         ) files)
  )

;; (metrics-server.api.files/task23 (metrics-server.api.files/get-files))

;; Задание 3
(defn task3 [files]
  (/ (reduce + (map (fn [file] (get file :size)) (filter (fn [a] (not (get a :directory))) files)))
  (count (filter (fn [a] (not (get a :directory))) files)))
  )

;; (metrics-server.api.files/task3 (metrics-server.api.files/get-files))

(defn -main [& args]
  (println (task21 (get-files)))
  (println (task22 (get-files)))
  (println (task23 (get-files)))
  (println (task3 (get-files)))
  )