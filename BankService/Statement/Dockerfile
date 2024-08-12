FROM bellsoft/liberica-openjre-alpine:17.0.9
EXPOSE 8080
WORKDIR /opt/app
ENV CLIENT_DEAL_URL=http://deal-mc:8081
ADD ./target/Statement.jar ./service.jar
CMD ["java", "-jar", "/opt/app/service.jar"]