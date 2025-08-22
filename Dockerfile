FROM tomcat:10.1

# Remove default webapps (optional but clean)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file into Tomcat's webapps folder
COPY voting_system.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080 for Render
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
