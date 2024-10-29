echo compilando o pacote
mvn clean package -Passembly-zip -DskipTests
echo subindo o artefato para o S3
aws s3 cp ./target/boletos-api-0.0.1-SNAPSHOT-lambda-package.zip s3://dgconsultbucket/artefatos/boletos-api-0.0.1-SNAPSHOT-lambda-package.zip
echo Atualizando a lambda
aws lambda update-function-code --function-name boletos-api-lambda --s3-bucket dgconsultbucket --s3-key artefatos/boletos-api-0.0.1-SNAPSHOT-lambda-package.zip --publish