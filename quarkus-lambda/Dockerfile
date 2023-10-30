# syntax=docker/dockerfile:experimental

FROM quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:22.3.3-java17 AS build
COPY --chown=quarkus:quarkus mvnw /code/mvnw
COPY --chown=quarkus:quarkus .mvn /code/.mvn
COPY --chown=quarkus:quarkus pom.xml /code/
USER quarkus
WORKDIR /code
RUN pwd
RUN ls -alh
RUN ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -T4
COPY src /code/src
RUN ./mvnw package -Dnative -DskipTests=true -T4


FROM mesosphere/aws-cli AS upload

WORKDIR /home/builduser/myapp/target

# fetching the file from "build" stage
COPY --from=build code/target/function.zip /home/builduser/myapp/target/function.zip

ARG FUNC_NAME

#
#RUN --mount=type=secret,id=aws,target=/root/.aws/credentials  \
#      aws lambda create-function \
#        --function-name ${FUNC_NAME} \
#        --zip-file /home/builduser/myapp/target/function.zip \
#        --handler ${HANDLER} \
#        --runtime ${RUNTIME} \
#        --role ${LAMBDA_ROLE_ARN} \
#        --timeout 15 \
#        --memory-size 256
##        ${LAMBDA_META}
RUN --mount=type=secret,id=aws,target=/root/.aws/credentials aws s3 cp --acl bucket-owner-full-control "./function.zip" "s3://test.lambda-package/${FUNC_NAME}"