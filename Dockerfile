FROM tomcat:10-jdk17
ADD target/touristagency.war /usr/local/tomcat/webapps/touristagency.war
EXPOSE 8080
CMD ["catalina.sh", "run"]