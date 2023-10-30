# All variables here should be without definition and it should
# be overwritten based on build environment

# Define the project name
variable "project" { default = "quarkus-native-user-score" }

# Lambda function bucket
variable "lambda_bucket" { default = "test.lambda-package" }

# Lambda function bucket path
variable "lambda_bucket_path" { default = "quarkus-native-user-store" }

# List of AWS Policies that is attached to your lambda roles that describe
# which permissions the lambda has when the function is executed by AWS.
variable "iam_policies" {
  default = [
    "LambdaBasic",
    "DynamoDBAccess"
  ]
}

# Define the lambda runtime. The runtime instruct AWS lambda service
# to provision the runtine for the function language and which version
# supported by AWS.
variable "runtime" { default = "provided.al2" }

# The timeout for your lambda to execute
variable "timeout" { default = "15" }

# The amount of memory allocated for your lambda function.
variable "memory" { default = "512" }

# The path to the lambda handler function for your runtime. This vary
# from language to language, but usually represents on which files and
# function AWS Lambda service will invoke.
variable "handler" { default = "edu.lambda.LambdaFunction::handleRequest" }

# Define the log_level for the lambda function.
variable "log_level" { default = "INFO" }

variable "dynamodb_table" { default = "UserScore"}

variable "region" {
  default     = "eu-central-1"
  description = "aws provider region"
}

variable "routing_account_id" { default = "159289031155" }
