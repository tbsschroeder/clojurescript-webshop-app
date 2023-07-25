# FROM theasp/clojurescript-nodejs:alpine as build
# WORKDIR /usr/src/app
# COPY deps.edn ./deps.edn
# RUN apk --no-cache add python alpine-sdk postgresql-dev && lein do deps, npm install
# COPY ./ /usr/src/app/
# RUN clj-M:watch

FROM node:alpine
WORKDIR /usr/src/app
ENV DB_NAME="clojure" DB_HOST="clojure" DB_USER="clojure" DB_PASSWORD="clojure" WEB_DOMAIN="app.example.com"
EXPOSE 80
CMD ["./run-server.sh"]
RUN apk --no-cache add libpq bash
COPY --from=build /usr/src/app/ /usr/src/app/