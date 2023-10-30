package edu.lambda.processing;

import edu.lambda.data.LambdaRequest;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
@Requires(beans = { DynamoDbClient.class })
public class UserScoreService {

    private static final String FILED_USER_ID = "userId";
    private static final String FILED_SCORE = "score";
    @Value("${dynamodb.tableName:UserScore}")
    private String dynamoDbTableName;

    DynamoDbClient dynamoDB;

    @Inject
    public UserScoreService(DynamoDbClient dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    public void add(LambdaRequest payload) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(FILED_USER_ID, AttributeValue.builder().n(payload.userId()).build());
        item.put(FILED_SCORE, AttributeValue.builder().n(payload.score()).build());
        log.info("Data is being saved {}, {}", dynamoDbTableName, item);

        var putItemResponse = dynamoDB.putItem(PutItemRequest.builder()
                .tableName(dynamoDbTableName)
                .item(item)
                .build());
        log.info("Data is saved {}", putItemResponse);
    }
}