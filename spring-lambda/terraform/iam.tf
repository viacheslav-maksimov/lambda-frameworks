# local variable used to simplify the variable interpolation and definition
locals {
  policy_prefix = "arn:aws:iam::${local.account_id}:policy/services"
}

# Create IAM role passed to the lambda function during execution.
data "aws_iam_policy_document" "lambda_trust" {
  statement {
    sid = replace(title(local.name), "-", "")
    effect = "Allow"

    principals {
      type = "Service"
      identifiers = [
        "lambda.amazonaws.com"]
    }

    actions = [
      "sts:AssumeRole"]
  }
}

# Creating the role with the lambda service trust policy. AWS IAM
# role needs a trust policy which define whom can assume the role and therefore
# used the permissions attached to the role.
resource "aws_iam_role" "lambda" {
  name = "lambda_${local.name}"
  path = "/services/"

  assume_role_policy = data.aws_iam_policy_document.lambda_trust.json
}

# Attaching policies to the created role. The count is used to count
# how many policies define on the iam_policies variables.
resource "aws_iam_role_policy_attachment" "policies" {
  role = aws_iam_role.lambda.name
  # role created above

  policy_arn = "${local.policy_prefix}/${var.iam_policies[0]}"
}

resource "aws_iam_role_policy_attachment" "lambda_dynamo_policy" {
  role       = aws_iam_role.lambda.name
  policy_arn = "${local.policy_prefix}/${var.iam_policies[1]}"
}

