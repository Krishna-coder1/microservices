#!/bin/bash

# Create a directory to store certificates and keys
mkdir -p secrets

# Generate CA private key
openssl genpkey -algorithm RSA -out secrets/ca-key.key

# Generate CA certificate
openssl req -new -x509 -key secrets/ca-key.key -out secrets/ca-certificate.pem -subj "/CN=Kafka CA" -days 3650

# Create a server keystore and certificate signing request (CSR)
keytool -genkey -noprompt -alias server -dname "CN=localhost" -storepass changeme -keypass changeme -keyalg RSA -keystore secrets/server.keystore.jks

# Generate a CSR for the server certificate
keytool -certreq -noprompt -alias server -keypass changeme -keystore secrets/server.keystore.jks -file secrets/server-csr

# Sign the server CSR with the CA certificate
openssl x509 -req -CA secrets/ca-certificate.pem -CAkey secrets/ca-key.key -in secrets/server-csr -out secrets/server-certificate.pem -days 365 -CAcreateserial

# Import the CA certificate into the server keystore
keytool -noprompt -keystore secrets/server.keystore.jks -alias CARoot -import -file secrets/ca-certificate.pem -storepass changeme

# Import the signed server certificate into the server keystore
keytool -noprompt -keystore secrets/server.keystore.jks -alias server -import -file secrets/server-certificate.pem -storepass changeme

# Create a server truststore and import the CA certificate
keytool -noprompt -keystore secrets/server.truststore.jks -alias CARoot -import -file secrets/ca-certificate.pem -storepass changeme

# Extract and save the key credentials to server-key.key

# Clean up the CSR file
rm secrets/server-csr
