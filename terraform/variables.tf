# All variables here should be without definition and it should
# be overwritten based on build environment

variable "region" {
  default     = "eu-central-1"
  description = "aws provider region"
}

# Lambda function bucket
variable "lambda_bucket" {
  default = "test.lambda-package"
}