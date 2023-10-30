# Declare a terraform provider which is the terraform module responsible
# to actually provision the infrastructure using the provider-target APIs
# and system.
provider "aws" {
  region = var.region
}

# Data provider to identify the current account number
data "aws_caller_identity" "current" {}