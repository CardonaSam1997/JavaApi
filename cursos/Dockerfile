FROM openjdk:21-jdk-slim as builder

WORKDIR /app/cursos

COPY ./pom.xml /app
COPY ./cursos/.mvn ./.mvn
COPY ./cursos/mvnw .
COPY ./cursos/pom.xml .

#Crear imagen con mas rapides
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./cursos/src ./src

RUN ./mvnw clean package -DskipTests

#Se crea una nueva imagen que almacene la primer imagen con todos los valores ya almacenados
#Merma el tamaño
FROM openjdk:21-jdk-slim
#FROM openjdk:17-jdk-alpine este saca error??

WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/cursos/target/cursos-0.0.1-SNAPSHOT.jar .
EXPOSE 8002

#entrypo
CMD ["java","-jar","cursos-0.0.1-SNAPSHOT.jar"]