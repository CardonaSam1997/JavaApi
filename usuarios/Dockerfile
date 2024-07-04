#darle un nombre a la imagen
#ARGUMENTOS: solo se usan en la construccion de la imagen
FROM openjdk:21-jdk-slim as builder
#Definir variables para DOCKER
ARG MSVC_NAME=usuarios
WORKDIR /app/$MSVC_NAME

COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

#Crear imagen con mas rapides
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -DskipTests

#Se crea una nueva imagen que almacene la primer imagen con todos los valores ya almacenados
FROM openjdk:21-jdk-slim

WORKDIR /app
RUN mkdir ./logs
#al ser imagenes diferentes, toca volver a crear el ARG para que lo reconozca
ARG MSVC_NAME=usuarios
ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/usuarios-0.0.1-SNAPSHOT.jar .
#ESTE ARGUMENTO NOS PERMITE CAMBIAR EL PUERTO AL CONSTRUIR LA IMAGEN
#docker build -t apodo -f path --build-arg PORT_APP=numero
ARG PORT_APP=8090
ENV PORT $PORT_APP
EXPOSE $PORT
#Cuando hagamos el despliegue del contenedor, este debe ser por el puerto 8000 para que funcione el servicio
#Hacemos referencia a la variable que estamos usando en el archivo .properties
#entrypo
CMD ["java","-jar","usuarios-0.0.1-SNAPSHOT.jar"]