FROM maven:latest AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY . ./
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
