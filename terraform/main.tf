resource "aws_dynamodb_table" "user_score_table" {
  name = "UserScore"
  hash_key = "userId"
  billing_mode = "PROVISIONED"
  read_capacity= "5"
  write_capacity= "5"

  attribute {
    name = "userId"
    type = "N"
  }
}

resource "aws_s3_bucket" "s3" {
  bucket = var.lambda_bucket
}