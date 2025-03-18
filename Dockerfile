# Use Payara as base image
FROM payara/server-full:latest

# Copy WAR file to Tomcat webapps directory
COPY target/rest.war $DEPLOY_DIR

# Expose Tomcat default port
EXPOSE 8080

# Start Payara Server
CMD ["bin/asadmin", "start-domain", "-v"]