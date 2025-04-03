FROM openjdk:17
EXPOSE 8083
ADD target/eureka-0.0.1-SNAPSHOT.jar eurekaserver.jar
ENTRYPOINT ["java", "-jar", "eurekaserver.jar"]