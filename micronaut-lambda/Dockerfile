# syntax=docker/dockerfile:experimental
#
# Docker multistage build provides the ability to use different image
# and steps to build and release your docker image and content.
#
# On this example our Dockerfile.build is responsible to build the
# lambda zip package and its required dependencies, and on a second
# stage release (upload) the package to S3 bucket.
#
# Check for details: https://docs.docker.com/develop/develop-images/multistage-build/

##
# Build stage
#  This docker stage will leverage docker build commands to generate the java jar file
#  that will be pack into the S3 package when creating the lambda function
# Description: ol8 - Oracle Linux 8
FROM ghcr.io/graalvm/graalvm-ce:ol8-java17-22.3.3 as build
RUN gu install native-image
RUN useradd -u 1000 -ms /bin/bash builduser

COPY mvnw /app/mvnw
COPY .mvn /app/.mvn
COPY pom.xml /app/

# Download maven dependencies so they can be cached
RUN ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline --batch-mode -T4

# Copy sources
COPY src /app/src
# Package
RUN ./mvnw package -Dapp.packaging=native-image --batch-mode -T4 -DskipTests

# Zip application
FROM amazonlinux:latest as zip
RUN yum update -y && yum install -y zip && yum clean all
WORKDIR /app/target
COPY --from=build /app/target/micronaut-lambda /app/target/func
RUN echo "#!/bin/sh" >> bootstrap && echo "set -euo pipefail" >> bootstrap && echo "./func -XX:MaximumHeapSizePercent=80 -Dio.netty.allocator.numDirectArenas=0 -Dio.netty.noPreferDirect=true -Djava.library.path=$(pwd)" >> bootstrap
RUN chmod 777 bootstrap
RUN chmod 777 func
RUN zip -j function.zip bootstrap func

# Upload zip to s3
FROM mesosphere/aws-cli AS upload
# fetching the file from "zip" stage
COPY --from=zip /app/target/function.zip /app/target/function.zip
ARG FUNC_NAME
WORKDIR /app/target

RUN --mount=type=secret,id=aws,target=/root/.aws/credentials aws s3 cp --acl bucket-owner-full-control "./function.zip" "s3://test.lambda-package/${FUNC_NAME}"
