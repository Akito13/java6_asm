FROM maven:3.8.5-openjdk-17 AS builder
COPY pom.xml .
RUN mvn verify --fail-never
COPY . .
RUN mvn package -Pprod -DskipTests
CMD ["java", "-jar", "target/qls.war"]

#FROM tomcat:10.1.9
#COPY --from=builder /target/qls.war /usr/local/tomcat/webapps
#EXPOSE 8080
#CMD ["catalina.sh", "run"]
