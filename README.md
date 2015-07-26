# ring.middleware.browser-caching

Middleware for ring to provide configurable cache control headers to browsers.

## Usage

Add this to your Leiningen `project.clj` dependencies:

[![Clojars Project](http://clojars.org/slester/ring-browser-caching/latest-version.svg)](http://clojars.org/slester/ring-browser-caching)

To use, simply provide a map of filetypes with the associated number of seconds you want to send for `Cache-Control: max-age=`.

```clojure
(ns app.core
  (:use  [ring.middleware.browser-caching])

(def app
  (wrap-browser-caching handler {"text/javascript" 360000
                                 "text/html" 0})
```


## License

Copyright © 2015 Stephen Lester

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
