resource "aws_lambda_function" "lambda" {
  function_name = local.name
  role = aws_iam_role.lambda.arn
  runtime = var.runtime
  handler = var.handler
  timeout = var.timeout
  memory_size = var.memory
  s3_bucket = var.lambda_bucket
  s3_key = var.lambda_bucket_path
  architectures = ["arm64"]

  # define environmental variables for the lambda function
  environment {
    variables = {
      DISABLE_SIGNAL_HANDLERS = true
      LOG_LEVEL = var.log_level
      TABLE_NAME = var.dynamodb_table
    }
  }
}