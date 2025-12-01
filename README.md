# Bajaj Finserv Health – Qualifier 1 (JAVA)

This repository contains my submission for the **Bajaj Finserv Health – JAVA Qualifier Round**.  
The task required building a Spring Boot application that:

- Generates a webhook on application startup  
- Retrieves a JWT access token  
- Determines the correct SQL question based on the last two digits of the registration number  
- Submits the final SQL query to the generated webhook URL  
- Runs completely without controllers or manual triggers  

Everything executes **automatically when the application starts**.

---

## 🚀 Tech Stack

- Java 17  
- Spring Boot 3.2.2  
- Maven  
- RestTemplate for API communication  

---

## Project Structure

```
qualifier-java/
 ├── pom.xml
 ├── src/main/java/com/bajaj/Application.java
 ├── src/main/java/com/bajaj/WebhookService.java
 ├── src/main/resources/
 └── target/qualifier-java-0.0.1-SNAPSHOT.jar

```

---


