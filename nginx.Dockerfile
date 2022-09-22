FROM nginx:1.20.2-alpine
COPY ./ssl/ip21sy5.crt /etc/nginx/ssl/ip21sy5.crt
COPY ./ssl/ip21sy5.key /etc/nginx/ssl/ip21sy5.key
