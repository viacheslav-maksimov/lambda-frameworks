export AWS_ACCESS_KEY_ID=$(aws configure get aws_access_key_id)
export AWS_SECRET_ACCESS_KEY=$(aws configure get aws_secret_access_key)

#logging in terraform
#export TF_LOG="DEBUG"

#{
#	"Version": "2012-10-17",
#	"Statement": [
#		{
#			"Effect": "Allow",
#			"Principal": {
#				"AWS": "arn:aws:iam::159289031155:user/auto1"
#			},
#			"Action": "*",
#			"Resource": "arn:aws:s3:eu-west-1:159289031155:accesspoint/my-access-point/*"
#		},
#	]
#}

#{
#    "Version": "2012-10-17",
#    "Id": "Policy1668771456228",
#    "Statement": [
#        {
#            "Sid": "Stmt1668771454663",
#            "Effect": "Allow",
#            "Principal": "*",
#            "Action": "s3:*",
#            "Resource": "arn:aws:s3:::b2x-lambda-package/*"
#        }
#    ]
#}

#build
mvn install -Dnative -DskipTests -Dquarkus.native.container-build=true

#deploy to lambda
#sh target/manage.sh update

#deploy to s3
aws s3 cp --acl bucket-owner-full-control ./target/function.zip s3://b2x-lambda-package/rnd-merchant-score

#terraform apply -var-file="qa-1.tfvars"