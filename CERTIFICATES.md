# Steps performed to generate self signed certificate authority

This file is not part of the default Android-Signal repository.
Generate new or buy certificates when deploying to a production setting.
Steps are added for review purposes:

```bash
# generate new certificate authority and server certificate

mkdir ~/cert && cd ~/cert
openssl req -new -nodes -text -out ca.csr -keyout ca-key.pem -subj "/CN=certificate-authority"
openssl x509 -req -in ca.csr -text -extfile /etc/ssl/openssl.cnf -extensions v3_ca -signkey ca-key.pem -out ca-cert.pem -days 3650
openssl x509 -in ca-cert.pem -text -noout

openssl req -new -nodes -text -out signal.appcraft.nl.csr -keyout signal.appcraft.nl-key.pem -subj "/CN=signal.appcraft.nl"
openssl x509 -req -in signal.appcraft.nl.csr -text -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out signal.appcraft.nl-cert.pem -days 3650
openssl x509 -in signal.appcraft.nl-cert.pem -text -noout

openssl req -new -nodes -text -out scdn.appcraft.nl.csr -keyout scdn.appcraft.nl-key.pem -subj "/CN=scdn.appcraft.nl"
openssl x509 -req -in scdn.appcraft.nl.csr -text -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out scdn.appcraft.nl-cert.pem -days 3650
openssl x509 -in scdn.appcraft.nl-cert.pem -text -noout

wget http://www.bouncycastle.org/download/bcprov-ext-jdk15on-160.jar

# use -nokeys option
openssl pkcs12 -export -out keystore.p12 -in ca-cert.pem -nokeys
keytool -import -trustcacerts -file ca-cert.pem -alias ca -destkeystore keystore.jks -storetype BKS -providerpath ./bcprov-ext-jdk15on-160.jar -provider org.bouncycastle.jce.provider.BouncyCastleProvider -storetype pkcs12

# list entries
keytool -storetype BKS -provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath ./bcprov-ext-jdk15on-160.jar -list -v -keystore keystore.jks

# rename for android and place into res/raw/whisper.store
mv keystore.jks whisper.store
```
