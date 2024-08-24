FROM amazoncorretto:21-alpine
WORKDIR /app

COPY rest/target/*.jar rest.jar

EXPOSE 8085
ENTRYPOINT ["java","-jar","rest.jar"]