FROM eclipse-temurin:18
LABEL authors="Titan"

WORKDIR /app
COPY pom.xml .mvn/pom.xml
RUN ./mvnw dependency:resolve
copy src ./src

CMD ["java "]

