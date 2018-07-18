# eIDAS WebApp 2.0

## Introduction

This project has received funding from the European Union's Connecting Europe Facility under grant agreement No INEA/OEF/ICT/A2016/1271348. and has been developed by the "Information Management Lab (i4M Lab)", which is part of the research group "ATLANTIS Group".

It is comprised of a Java WebApp that streamlines and greatly speeds up the integration of a Service Provider to the Greek eIDAS node, in such a way that no
knowledge of the eIDAS SAML  profile is required. Additionally, it provides pre-built UI for guiding the users through eIDAS authentication flow.

It was developed by the "Information Management Lab (i4M Lab)", participant of the Atlantis Group (http://www.atlantis-group.gr/) of the University of the Aegean (https://www.aegean.gr/).

## Project Purpose

The eIDAS WebApp 2.0 facilitates the integration of a SP with the eIDAS node by:

- Allowing the SP to avoid development time for processing SAML messages

- Completely handling an eIDAS-based authentication flow (including UIs)

- Being SP infrastructure independent; operates over a simple REST API

- Providing strong security assertions in the form of JWT ( RFC 7519)

Important Note: In order to use the WebApp 2.0 for integration with the eIDAS GR node, it needs to be deployed in the same domain as the SP

## Deployment

Internally the eIDAS WebApp 2.0 uses the eIDAS SAML library presented https://github.com/ellak-monades-aristeias/eIDAS-SP-SAML-Tools-Library . Thus, the same steps presented in the deployment of the SAML library are required. These can be summed up to:

- The copying of the configEidas folder
- Configure the keystore (eidasKeystore.jks)
- Edit the configuration files (SignModule_SP.xml, EncryptModule_SP.xml, sp.properties)

After configuring the SAML library we can proceed with the configuration of the eIDAS WebApp 2.0.
This WebApp is also offered as a Docker image. Thus, in order to deploy the WebApp 2.0 the hosting machine must have a functional Docker engine.
For instructions of how to setup Docker please refer to: https://docs.docker.com/install/ and follow the installation instructions depending on your hosting system (linux, windows, mac). Additionally for easier configuration of the container it is assumed that Docker compose is also installed (for instructions on installing docker compose please also refer to: https://docs.docker.com/compose/install/ ) although it is not a requirement.

```
version: '3'

services:

loginWebApp2:

image: endimion13/eidas-gr-loginwebapp:3.1

ports:

- 9080:8090

- 9090:8090

environment:
environment:
    - EIDAS_PROPERTIES=CurrentFamilyName,CurrentGivenName,DateOfBirth,PersonIdentifier
    - SP_FAIL_PAGE=http://eideusmartclass.aegean.gr/authFail
    - SP_SUCCESS_PAGE=http://eideusmartclass.aegean.gr/eIDASSuccess
    - SP_LOGO=/img/logo2.png
    - ISS_URL=https://eidasiss.aegean.gr:8081/ISS2/ValidateToken
    - ISS_PRE_URL=https://eidasiss.aegean.gr:8081/ISS2/ValidateToken
    - SP_SERVER=https://eideusmartclass.aegean.gr
    - SP_ID=sp1
    - SP_SECRET=
    - AUTH_DURATION=43800
    - UAEGEAN_LOGIN=true
    - CLIENT_ID=867kszvon99qp4
    - REDIRECT_URI=https://eideusmartclass.aegean.gr/eIDASSuccess/linkedInResponse
    - LINKED_IN_SECRET=  
    - LINKED_IN=true
    - URL_PREFIX=/eidasLogin
    - UAEGEAN_AP=https://eidasiss.aegean.gr:8081/ISS2/ldap.jsp
    - HTTP_HEADER=true
    - ASYNC_SIGNATURE = true
    - SP_JWT_CERT = path to private key keystore
    - SP_KEY_PASS = password for the certificate;
    - STORE_PASS = password for the keystore
    - CERT_ALIAS = name of the certificate in the keystore
volumes:
- /configEidas:/configEidas
- ./webappConfig:/webappConfig

```

The various parts of this file are explained below:

- Version: denotes the syntax version of the composer file (up to the user for compatibility with the examples provided in this document please use 2 or 3
- Services: denotes the start of the service sector of the compose file
- loginWebApp2: denotes the name of the Thin WebApp 2.0 service name (up to the user to pick a friendly name). Format; hostmachineport:containerport
- Image: the image to use to build the container
- Ports: list of host machine ports that will be mapped to container ports (note that you should always map to container port 8090)
- environment: list of environmental variables that will be added to the container
  - EIDAS_PROPERTIES: comma separated properties the SP requires from eIDAS
  - SP_FAIL_PAGE: e-Diploma Supplement url to redirect to in case of authentication failure
  - SP_SUCCESS_PAGE: e-Diploma Supplement url to redirect to in case of authentication success
  - SP_LOG: url of the logo that will be displayed at the header of the UIs
  - ISS_URL: url of the instance of ISS 2.0 that the WebApp module is connected to pointing to the endpoint ValidateToken of ISS 2.0
  - ISS_PRE_URL: ISS 2.0 url connected to the preproduction eIDAS node for tests (if available, else the same as ISS_URL)
  - SP_SERVER: the url of the SP home page (i.e. https://eideusmartclass.aegean.gr)
  - SP_ID: the id of the SP as that was configured in ISS 2.0
  - SP_SECRET: string that will be used on HS256 signature of the generated assertions that will be propagated to the SP.
  - AUTH_DURATION: integer denoting the duration for which the authentication cookie will be stored in milliseconds (if no value is given the cookie will not expire)
  - UAEGEAN_LOGIN: boolean, denote whether authentication using the UAegean Identity Provider is allowed
  - LINKED_IN: boolean, denotes if LinkedIn authentication is allowed
  - CLIENT_ID: client id if LinkedIn authentication is allowed
  - REDIRECT_URI: url to redirect to the result of a LinkedIn authentication process
  - LINKED_IN_SECRET: the secret share between the LinkedIn and the SP in case of LinkedIn authentication
  - URL_PREFIX: denotes (if available) be a prefix that should be added to the relative urls inside the UI conmponents of e-Diploma Supplement Service web app, in cache of proxy Deployment
  - UAEGEAN_AP: url of the UAegean Identity provider authentication service
  - HTTP_HEADER: boolean, denotes if the JWT should be send as a cookie or as an HTTP authentication header
  - ASYNC_SIGNATURE: boolean, denotes if the siging of the JWT should be made asynchronously (i.e. private/public key with RSA) or synchronously with HS256
  - SP_JWT_CERT: denotes the path to the private keystore containing the private key used to sign the JWT token (if available)
  - SP_KEY_PASS; the password to the aforementioned keystore
  - CERT_ALIAS: denotes the alias of the certificate that is to be used from the keystore to signe the JWT token (if available)
- volumes: list of native directories that will be mounted as container directories. Format: nativeDir:containerDir
  - the configEidas directory contains the required configuration files for the interaction with the eIDAS Node
  - the webAppConfig directory contains files for the configuration of the WebApp

### Configuration files

The contents of the configEidas.zip file must be extracted and copied to a directory in the local file system and mounted in the container at the folder /configEidas.
#### Setting up the keystore
The aforementioned directory contains the file eidasKeystore.jks, which must contain all the necessary certificates for secure and trusted communication with the eIDAS Node. The following steps need to be executed in order to prepare the keystore for operation.
1. Change the keystore password (current password: “local-demo”)
2. Obtain a certificate which identifies the SP. The certificate must satisfy the criteria described in the eIDAS - Cryptographic requirements for the Interoperability Framework document , regarding SAML signing certificates.
3. Import the certificate in the keystore as a PrivateKeyEntry
4. Provide the Greek eIDAS Node team with the public certificate of the SP, to be added to the Greek eIDAS Node list of trusted SPs.
#### Configuring the eIDAS SP SAML Tools Library
The first step in configuring the eIDAS SP SAML Tools Library requires the modification of a few configuration files. These files are located in the root folder of the underlying [SP-eIDAS-SAMLTools](LEPS-APIs/eIDAS-SP-WebApp-2.0/libs/gr/eidas/connector13/1.0/connector13-1.0.jar) library used by this module. In each of the following configuration files, replace the existing entries with the following information:

SignModule_SP.xml:
```
    <entry key="response.sign.assertions">true</entry>
    <entry key="keyStorePath"> eidasKeystore.jks</entry>
    <entry key="keyStorePassword">keystore_password</entry>
    <entry key="keyPassword">SP_certificate_password</entry>
    <entry key="issuer">SP_certificate_issuer</entry>
    <entry key="serialNumber">SP_certificate_serial_number</entry>
    <entry key="keyStoreType">JKS</entry>

    <entry key="metadata.keyStorePath"> eidasKeystore.jks</entry>
    <entry key="metadata.keyStorePassword">keystore_password</entry>
    <entry key="metadata.keyPassword">SP_certificate_password</entry>
    <entry key="metadata.issuer">SP_certificate_issuer</entry>
    <entry key="metadata.serialNumber">SP_certificate_serial_number</entry>
    <entry key="metadata.keyStoreType">JKS</entry>
```    
EncryptModule_SP.xml:
```
<!-- Key Encryption algorithm -->
<entry key="key.encryption.algorithm">http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p</entry>
<entry key="keyStorePath"> eidasKeystore.jks</entry>
<entry key="keyStorePassword">keystore_password</entry>
<entry key="keyPassword">SP_certificate_password</entry>

...

<!--  If not present then no decryption will be applied on response -->
<!-- Certificate containing instance private key-->
<entry key="responseDecryptionIssuer">SP_certificate_issuer</entry>
<entry key="serialNumber">SP_certificate_serial_number</entry>
<entry key="keyStoreType">JKS</entry>
```
sp.properties:
```
sp.return=URL of the return page (e.g.: http://84.205.248.180:80/ReturnPage.jsp).
This page receives and processes the authentication response data from the eIDAS Infrastructure.
….
sp.metadata.url=URL of the metadata page (e.g.:http://84.205.248.180:80/metadata.jsp). This is the URL under which the already provided metadata.jsp page is located
…..
sp.qaalevel=#
The level of Assurance required by this SP for the provided authentication data.
1=Non-existent
2=Low
3=Substantial
4=High
```

Deployment now is simply a matter of starting the services defined in such a compose file, for example: docker-compose -f loginService.yml up

## Integration
---
For the rest of this document we assume that the WebApp 2.0 is deployed at http://www.host.com:8090.

In order for the SP to interact with the WebApp 2.0 it needs to:
- Upon authentication request, redirect the user to http://www.host.com:8090/login
- Build an endpoint (e.g. http://www.host.com/authsuccess) that the WebApp 2.0 will redirect to in case of authentication success
- (optionally) Build an endpoint (e.g. http://www.host.com/authfail ) that the WebApp 2.0 will redirect to in case of authentication failure

The success endpoint consumes the JSON Web Token (JWT) generated by the WebApp delivered to the SP in the form of a cookie. JSON Web Tokens are an open, industry standard RFC 7519 method for representing claims securely between two parties. In the case of a successful authentication this token will contain the retrieved eIDAS identification attributes of the user. In case of an authentication failure this token will contain the authentication error as that was returned by the ISS 2.0. The generated authentication token is signed using HS256 (HMAC with SHA-256) with a secret shared between the e-Diploma Supplement service and the WebApp.

Upon receiving the token the SP should validate it and read the identification attributes from its payload. The format of the generated JWT token payload is presented as an example below:
```
{
  "sub": "{\"eid\":\"GR/GR/ERMIS-11076669\",
  \"personIdentifier\":\"GR/GR/ERMIS-11076669\",
  \"dateOfBirth\":\"1980-01-01\",\"currentFamilyName\":\"ΠΕΤΡΟΥ,PETROU\",
  \"currentGivenName\":\"ΑΝΔΡΕΑΣ,ANDREAS\"}",
  "origin": "eIDAS"
}
```
This JWT contains two claims the "sub" which contains the actual eIDAS attributes and the "origin", which contains the source of the identification (e.g. eIDAS, LinkedIn etc.) In case of an error in the authentication process no JWT is generated, but the type of the error is handled internally by the WebApp and an appropriate message is presented to the user.

## Repository Contents
---
- **src**, this repository folder contains the micro-service responsible for integrating the with the Greek eIDAS node
- **deploy.yml**, this file contains an example configuration file that can be used as a starting point to deploying your application
