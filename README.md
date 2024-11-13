# Key Management Entity (KME) Simulator

## Description
This application implements the communication protocol described in ETSI GS QKD 014:
Quantum Key Distribution (QKD); Protocol and data format of REST-based key delivery API
It has 2 modules: 
* Key Management Entity (KME) simulator
* Secure Application Entity (SAE) simulator

## KME simulator
It simulates multiple KMEs. 

Current release implements only GET access methods for the REST API. 

### Usage
Because this release does not implement also the specification from ETSI 004, 
every request should contain the SAE ID (SAE-ID) in header.

`curl -v -H 'SAE-ID: SAE-A' http://localhost:8080/api/v1/keys/SAE-B/status`

`curl -v -H 'SAE-ID: SAE-A' http://localhost:8080/api/v1/keys/SAE-B/enc_keys`

`curl -v -H 'SAE-ID: SAE-B' http://localhost:8080/api/v1/keys/SAE-A/dec_keys`

Next releases will add more features:
* POST access methods
* communication between simulators installed in a network 
* accepting commands (e.g. generate keys)
* loading configuration files

## SAE simulator
It simulates requests to KME simulator.

### Usage
SAE simulator exposes a REST API which triggers the requests to KME simulator. 
* http://localhost:8081/status
* http://localhost:8081/enc
* http://localhost:8081/dec