# Generate certificates

## Description
Simulator uses certificate authentication. 
Here are some commands for generating keys and certifications for both parties (simulator and clients), 
using [openssl](https://wiki.openssl.org/index.php/Binaries) library.
Also, there are commands for creating a keystore repository that application will use to hold the server's private key and certificate, 
using [keytool](https://docs.oracle.com/en/java/javase/23/docs/specs/man/keytool.html).

### Server

#### self-sign root CA(Certificate Authority) certificate
`openssl req -x509 -sha256 -days 3650 -newkey rsa:4096 -keyout rootCA.key -out rootCA.crt`

#### certificate signing request (CSR) for server
`openssl req -new -newkey rsa:4096 -keyout kme-a.key -out kme-a.csr`

#### sign the request with rootCA.crt certificate and its private key
`openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in kme-a.csr -out kme-a.crt -days 365 -CAcreateserial`

#### package server’s private key together with the signed certificate
`openssl pkcs12 -export -out kme-a.p12 -name "KME-A" -inkey kme-a.key -in kme-a.crt`

#### create a keystore.jks repository and import the kme-a.p12 file
`keytool -importkeystore -srckeystore kme-a.p12 -srcstoretype PKCS12 -destkeystore keystore.jks -deststoretype JKS`

#### create a truststore.jks file and import the rootCA.crt
`keytool -import -trustcacerts -noprompt -alias ca -ext san=dns:localhost,ip:127.0.0.1 -file rootCA.crt -keystore truststore.jks`

### Client (application that want to connect to our simulator)

#### certificate signing request (CSR) for the client
`openssl req -new -newkey rsa:4096 -nodes -keyout sae-a.key -out sae-a.csr`

### sign the request with rootCA.crt certificate and its private key
`openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in sae-a.csr -out sae-a.crt -days 365 -CAcreateserial`
