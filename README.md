Oumaima Amdouni
# ✈️ EasyTrip - Microservice de Gestion des Réservations

Ce microservice fait partie du projet **EasyTrip** et permet la gestion complète des **réservations de voyage**. Il inclut des fonctionnalités telles que la création, la modification, la suppression, les recommandations personnalisées, la génération de tickets PDF avec QR code et l’ajout d’options de voyage.

---

## 🛠 Technologies utilisées

- Java 17
- Spring Boot 3.2.4
- Spring Data JPA
- Spring Cloud OpenFeign
- Spring Cloud Netflix Eureka
- MySQL
- ZXing (QR code)
- OpenPDF

---

## ⚙️ Configuration

### 📦 `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/easytrip_db?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

spring.application.name=reservation-service
server.port=8085

eureka.client.service-url.defaultZone=http://localhost:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
