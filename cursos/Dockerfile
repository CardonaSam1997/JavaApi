FROM openjdk:21-jdk-slim as builder
ARG MSVC_CURSOS=cursos
WORKDIR /app/$MSVC_CURSOS

COPY ./pom.xml /app
COPY ./$MSVC_CURSOS/.mvn ./.mvn
COPY ./$MSVC_CURSOS/mvnw .
COPY ./$MSVC_CURSOS/pom.xml .

#Crear imagen con mas rapides
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MSVC_CURSOS/src ./src

RUN ./mvnw clean package -DskipTests

#Se crea una nueva imagen que almacene la primer imagen con todos los valores ya almacenados
#Merma el tamaño
FROM openjdk:21-jdk-slim
WORKDIR /app
#CREACION DE LA CARPETA LOGS
RUN mkdir ./logs

ARG MSVC_CURSOS=cursos
ARG TARGET_FOLDER=/app/$MSVC_CURSOS/target

COPY --from=builder $TARGET_FOLDER/cursos-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8091
ENV PORT $PORT_APP
EXPOSE $PORT

#entrypoint
CMD ["java","-jar","cursos-0.0.1-SNAPSHOT.jar"]