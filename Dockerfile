FROM openjdk:21-slim
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests
ENTRYPOINT [ "java", "-jar", "target/To-Do-List-0.0.1-SNAPSHOT.jar" ]