FROM openjdk:17-ea-3-jdk-oracle
LABEL authors="Boburjon"
ENTRYPOINT ["top", "-b"]
ADD build/libs/plane-ticket-purchase-system.jar plane-ticket-purchase-system.jar
RUN["java","-jar","plane-ticket-purchase-system.jar"]