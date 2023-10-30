# Declare a terraform provider which is the terraform module responsible
# to actually provision the infrastructure using the provider-target APIs
# and system.
provider "aws" {
  region = var.region
}

# Data provider to identify the current account number
data "aws_caller_identity" "current" {}

# local variable used for the terraform code. This variable is available for
# every terraform code inside the project.
locals {
  name = var.project
  account_id = data.aws_caller_identity.current.account_id
}
