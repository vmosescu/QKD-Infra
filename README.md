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
We can use `curl` to test our simulator.

`curl -v -k --cert sae-a.crt --key sae-a.key https://localhost:8443/api/v1/keys/SAE-B/status`

`curl -v -k --cert sae-a.crt --key sae-a.key https://localhost:8443/api/v1/keys/SAE-B/enc_keys`

`curl -v -k --cert sae-b.crt --key sae-b.key https://localhost:8443/api/v1/keys/SAE-A/dec_keys`

Next releases will add more features:
* POST access methods
* communication between simulators installed in a network 
* accepting commands (e.g. `generate keys`)
* loading configuration files
