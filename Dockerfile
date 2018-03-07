FROM azul/zulu-openjdk:8

LABEL maintainer="glebzhebrakov@gmail.com"

#install required dependencies
WORKDIR workspace
COPY build/libs/*.jar /workspace/microservice.jar
COPY entrypoint.sh /workspace/entrypoint.sh
ENTRYPOINT ["/workspace/entrypoint.sh", "-jar", "/workspace/microservice.jar"]
