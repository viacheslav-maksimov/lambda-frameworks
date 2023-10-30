# Lambda to upload user score
#### Tech Stack

* Java 17
* Micronaut 4.0
* 


#### Deployment info

This service is deployed to `b2x` AWS account.

#### Quality

[![Quality Gate Status](https://sonar.qa.services.auto1.team/api/project_badges/measure?project=wkda.api.lambda%3Asales-reps-upload-lambda&metric=alert_status)](https://sonar.qa.services.auto1.team/dashboard?id=wkda.api.lambda%3Asales-reps-upload-lambda)

[Sonar](https://sonar.qa.services.auto1.team/dashboard?id=wkda.api.lambda%3Asales-reps-upload-lambda)

#### Application Cockpit

* [App Cockpit QA](https://cockpit.qa.apps.auto1.cloud/catalog/default/component/sales-reps-upload-lambda)
* [App Cockpit PROD](https://cockpit.prod.apps.auto1.cloud/catalog/default/component/sales-reps-upload-lambda)


#### Deploy Jobs

* [Jenkins QA](https://jenkins.qa.b2x.auto1.team/job/java/job/rem/job/sales-reps-upload-lambda/job/)
* [Jenkins PROD](https://jenkins.prod.apps.auto1.team/job/java/job/rem/job/sales-reps-upload-lambda/)

#### Monitoring

##### QA

* [Kibana](https://kibana.qa.services.auto1.team/app/discover#/?_g=%28filters:!%28%28%27$state%27:%28store:appState%29,meta:%28alias:!n,disabled:!f,index:%27*beat-*%27,key:service.name,negate:!f,params:%28query:sales-reps-upload-lambda-qa-1%29,type:phrase%29,query:%28match_phrase:%28service.name:sales-reps-upload-lambda-qa-1%29%29%29%29,refreshInterval:%28pause:!t,value:0%29,time:%28from:now-15m,to:now%29%29&_a=%28columns:!%28%29,filters:!%28%29,index:%27*beat-*%27,interval:auto,query:%28language:kuery,query:%27%27%29,sort:!%28!%28%27@timestamp%27,desc%29%29%29)
* [Grafana](https://grafana.qa.services.auto1.team/d/AWSLambda/aws-lambda?orgId=1&var-alias=b2x-qa&var-region=eu-west-1&var-functionname=sales-reps-upload-lambda-qa-1)

##### PROD

* [Kibana](https://kibana.prod.services.auto1.team/app/discover#/?_g=%28filters:!%28%28%27$state%27:%28store:appState%29,meta:%28alias:!n,disabled:!f,index:%27*beat-*%27,key:service.name,negate:!f,params:%28query:sales-reps-upload-lambda-qa-1%29,type:phrase%29,query:%28match_phrase:%28service.name:sales-reps-upload-lambda-qa-1%29%29%29%29,refreshInterval:%28pause:!t,value:0%29,time:%28from:now-15m,to:now%29%29&_a=%28columns:!%28%29,filters:!%28%29,index:%27*beat-*%27,interval:auto,query:%28language:kuery,query:%27%27%29,sort:!%28!%28%27@timestamp%27,desc%29%29%29)
* [Grafana](https://grafana.prod.services.auto1.team/d/AWSLambda/aws-lambda?orgId=1&var-alias=b2x-qa&var-region=eu-west-1&var-functionname=sales-reps-upload-lambda-prod)

---

## My-readme. Native option
> 1. make build

build and create function.zip
> 2. make upload

upload function.zip to s3
>3. terraform apply -auto-approve

create aws infra and deploy function.zip from s3 to lambda