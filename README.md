Example of frameworks to compare native image performance


1) terraform apply
2) ${project-dir}/make upload (compile and upload a package in s3)
3) ${project-dir}/make tf(deploy lambda)
4) ${project-dir}/make run(run a request)
5) ${project-dir}/make refresh(refresh lambda in order to use cold start again)