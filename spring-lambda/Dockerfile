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
#RUN ./mvnw clean -Pnative package --batch-mode -T4 -DskipTests
RUN ./mvnw clean -Pnative native:compile --batch-mode -T4 -DskipTests

# Upload zip to s3
FROM mesosphere/aws-cli AS upload
# fetching the file from "zip" stage
COPY --from=build /app/target/function.zip /app/target/function.zip
ARG FUNC_NAME
WORKDIR /app/target

RUN --mount=type=secret,id=aws,target=/root/.aws/credentials aws s3 cp --acl bucket-owner-full-control "./function.zip" "s3://test.lambda-package/${FUNC_NAME}"
