FROM java:8
RUN mkdir /app
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "waiting.jar"]
