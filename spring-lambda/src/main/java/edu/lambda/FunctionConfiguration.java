package edu.lambda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@SpringBootApplication
public class FunctionConfiguration{
    private static final String AWS_REGION = "eu-central-1";

    /*
     * You need this main method (empty) or explicit <start-class>example.FunctionConfiguration</start-class>
     * in the POM to ensure boot plug-in makes the correct entry
     */
    public static void main(String[] args) {
        // empty unless using Custom runtime at which point it should include
        // SpringApplication.run(FunctionConfiguration.class, args);
        SpringApplication.run(FunctionConfiguration.class, args);
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder().region(Region.of(AWS_REGION)).build();
    }
}
