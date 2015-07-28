(ns ring.middleware.browser-caching
  (:require [ring.util.response :as response]
            [clojure.string :as str]))

(def http-cache-control "Cache-Control")

(defn- has-cache-headers? [res]
  "Does the response already have cache control headers?"
  (some? (get-in res [:headers http-cache-control])))

(defn- get-content-type [res]
  "Gets the Content-Type from the response's headers."
  (some-> (get-in res [:headers "Content-Type"]) (str/split #";") first))

(defn wrap-browser-caching [handler filetypes]
  {:pre [(map? filetypes)]}
  "Executes the handler using the filetype map found in the second parameter.
  The filetypes map should map extension to the number of seconds desired for max-age, e.g. {\"text/javascript\" 3600}"
  (fn [req]
    (let [res (handler req)
          headers (res :headers)
          max-age (filetypes (get-content-type res))]
      ;; Only send if cache headers don't already exist.
      (if (or (has-cache-headers? res) (not max-age))
      res
      (response/header res http-cache-control (format "max-age=%d" max-age))))))
