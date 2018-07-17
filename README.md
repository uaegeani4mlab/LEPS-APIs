![LEPS Project Logo](http://www.leps-project.eu/sites/leps-project.eu/themes/zen/leps/images/logo_negativo.svg)

# LEPS APIs by UAegean i4m available

 This project has received funding from the European Union's Connecting Europe Facility under grant agreement No INEA/OEF/ICT/A2016/1271348. and has been developed by the "Information Management Lab (i4M Lab)", which is part of the research group "ATLANTIS Group".

### Project Scope

 This repository contains the source code of an intermediary connection facility for the integration of a Service Provider (SP) to eIDAS Network, i.e. an eIDAS API Connector that allows SPs to easily and transparently interoperate with an eIDAS Node (this applying to “proxy countries”). Through this API Connector (initially named Interconnection Supporting Service), a SP can formulate authentication requests to be sent to the eIDAS-Node and receive “pre-processed” authentication responses, while investing minimal technical and organizational effort. The API Connector developed in this project re-uses the essential of the functionality of eIDAS Demo SP package (part of the sample eIDAS implementation provided by CEF).

### Repository Contents

LEPS API Connector provides a method to connect to an eIDAS (proxy-based) Node depending on specific SP requirements:
* **eIDAS SAML Tools Lib** This module can be used by Java-based SPs to be integrated in the application to build an eIDAS solution from scratch
* **eIDAS SP WebApp 2.0** This module allows an SP to avoid development time for processing SAML messages, Completely handles an eIDAS-based authentication flow (including UIs),  operates over a simple REST API  and provides in JWT based security
* **eIDAS Inteconnection Supporting Service 2.0 (eIDAS ISS 2.0)** This module allows an SP to avoid development time for processing SAML messages, supports the interconnection of many SP services in the same domain, sends SAML 2.0 request to eIDAS Node, translates response from SAML 2.0 to JSON and other common enterprise standards(WSDL) and forwards it to the relevant SP service, allows for multiple services with the same SPs sharing one certificate
