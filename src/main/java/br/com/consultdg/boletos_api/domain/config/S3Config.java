package br.com.consultdg.boletos_api.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
    @Bean
    public AmazonS3 amazonS3() {
        // BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        return AmazonS3ClientBuilder.standard()
                // .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-east-1")
                .build();
    }

}
