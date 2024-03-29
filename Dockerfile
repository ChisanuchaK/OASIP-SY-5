FROM node:alpine as build
WORKDIR /app
COPY ./TimeupFrontend/package*.json /app/
RUN ls
RUN npm i
COPY ./TimeupFrontend .
RUN npm run build

FROM nginx:1.20.2-alpine
COPY  --from=build /app/dist /usr/share/nginx/html
COPY  ./TimeupFrontend/frontend.conf /etc/nginx/conf.d/default.conf
EXPOSE 80