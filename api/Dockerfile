FROM alpine:latest
RUN apk add --no-cache openjdk21
WORKDIR /app
COPY farmproducts-api/target/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]