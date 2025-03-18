# Use Payara as base image
FROM payara/server-full:latest

# Copy WAR file to Payara webapps directory
COPY target/rest.war $DEPLOY_DIR

# Expose Payara default port and MySQL port
EXPOSE 8080 3306 4848

# Start Payara Server
CMD ["bin/asadmin", "start-domain", "-v"]