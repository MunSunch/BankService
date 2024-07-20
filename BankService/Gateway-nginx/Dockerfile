FROM nginx
EXPOSE 8080
ADD ./config /etc/nginx/templates
ADD ./html /usr/share/nginx/html
ENV DEAL_MC=${DEAL_MC}
ENV STATEMENT_MC=${STATEMENT_MC}