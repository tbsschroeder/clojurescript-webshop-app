# Webshop App in CLJS

This webshop app is built with [ClojureScript](https://clojurescript.org/) on
[reframe](http://day8.github.io/re-frame/). It's also an alternative frontend for my
[Clojure Webshop App](https://github.com/tbsschroeder/clojure-webshop-app).

## Requirements

Short and crisp: `npm` and `clj`.

## Setup

First, the app needs the database from my former project [Clojure Webshop App](https://github.com/tbsschroeder/clojure-webshop-app).
There just hit

    docker-compose up

Second, install the dependencies with npm:

    npm install

Third, you can start the frontend service with:

    clj -M:watch

You find the application at [http://localhost:8700](http://localhost:8700). Open re-frame 10x with `Ctrl+h`.

### Helper

You can run multiple helping tools like code formatting, linting, interactive
dependency checker and code analyzer via:

    clj -A:cljfmt fix
    clj -A:eastwood
    clj -A:antq
    clj -A:kibit

## Tests
_under construction_

There are sample tests, which can be run with

    lein doo ??

which starts an ClojureScript autobuilder for the test profile and runs slimerjs on it when it's done.


## Demo

![sample](img/demo.png)



## License
___
*All images are under the copyright of [METRO AG](https://www.metroag.de/).*
___

The MIT License (MIT)

*Copyright © 2023 Dr. Tobias Schröder*

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
