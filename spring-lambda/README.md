
#### Tech Stack

* Java 17
* Spring

### Description
https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html#native-image.developing-your-first-application.buildpacks.system-requirements
### with installed graalvm 
* mvn -Pnative native:compile
* mvn -Pnative spring-boot:build-image

## My-readme. Native option
> 1. make build

build and create function.zip
> 2. make upload

upload function.zip to s3
>3. terraform apply -auto-approve

create aws infra and deploy function.zip from s3 to lambda