FROM tomcat:9.0-jdk17

# Remove default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file to webapps directory
COPY target/CinemaBookingAdminPanel.war /usr/local/tomcat/webapps/ROOT.war

# Copy MySQL Connector to Tomcat lib directory
COPY target/dependency/mysql-connector-java-8.0.33.jar /usr/local/tomcat/lib/

# Copy context.xml with database configuration
COPY src/main/webapp/META-INF/context.xml /usr/local/tomcat/conf/context.xml

EXPOSE 8080

CMD ["catalina.sh", "run"]
