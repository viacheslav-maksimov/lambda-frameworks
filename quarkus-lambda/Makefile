.PHONY: build upload tf

# Variables used for the build stage
BUILD_CMD = docker build
BUILD_FILE = -f Dockerfile
BUILD_CONTEXT = .
FUNC_NAME = quarkus-native-user-score
REGION = eu-central-1
BUILD_OPTS = --no-cache --progress=plain --target=build -t $(FUNC_NAME)
UPLOAD_OPTS = --no-cache --force-rm --target=upload --build-arg=FUNC_NAME=$(FUNC_NAME) --secret id=aws,src=${HOME}/.aws/credentials -t $(FUNC_NAME)

# Define here the steps to build your lambda package
build:
	$(BUILD_CMD) $(BUILD_OPTS) $(BUILD_FILE) $(BUILD_CONTEXT)

upload:
	$(BUILD_CMD) $(UPLOAD_OPTS) $(BUILD_FILE)  $(BUILD_CONTEXT)

tf:
	terraform -chdir=${HOME}/projects/myprojects/performance-lambda/quarkus-lambda/terraform apply -auto-approve -replace=aws_lambda_function.lambda

run:
	aws lambda invoke \
	 --region $(REGION) \
     --function-name $(FUNC_NAME) \
     --cli-binary-format raw-in-base64-out \
     --payload '{ "userId": "2", "score": "2"}' \
     response.json --log-type Tail \
     --query 'LogResult' --output text --cli-binary-format raw-in-base64-out | base64 --decode

refresh:
	aws lambda update-function-configuration --region $(REGION) --function-name $(FUNC_NAME) --description "foo quarkus"