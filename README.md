# technical-assessment

A Small clojure app submitted as the technical assessment of an interview

## Prerequisites

This app requires the following to be installed:
- Java
- Leiningen
- Docker and Docker Compose

## Usage

1. Run `docker-compose up` to start up kafka and zookeeper.
2. Once both kafka and zookeeper logs have settled, run `lein run` to start the application.
3. Hit `localhost:8080/scores` in the browser to see the aggregate scores as JSON, or hit `localhost:8080/simulator`.
   for a form used to simulate a series of events of various types, after which you can hit the `/scores` URL again and
   see therein the updated aggregate values.

## License

Copyright © 2018 Jeremy Holland

Distributed under the Eclipse Public License either version 1.0 or any later version.
