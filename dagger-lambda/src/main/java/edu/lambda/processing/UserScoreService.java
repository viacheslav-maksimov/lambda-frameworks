package edu.lambda.processing;

import edu.lambda.data.LambdaRequest;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Singleton
public class UserScoreService {

    private static final String FILED_USER_ID = "userId";
    private static final String FILED_SCORE = "score";

    private static final String DYNAMO_DB_TABLE_NAME = "UserScore";
    private static final String REGION = "eu-central-1";

    @Inject
    public UserScoreService() {
    }

    public void add(LambdaRequest payload) {
        var dynamoDb = DynamoDbClient.builder().region(Region.of(REGION)).build();
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(FILED_USER_ID, AttributeValue.builder().n(payload.userId()).build());
        item.put(FILED_SCORE, AttributeValue.builder().n(payload.score()).build());
        log.info("Data is being saved {}, {}", DYNAMO_DB_TABLE_NAME, item);

        var putItemResponse = dynamoDb.putItem(PutItemRequest.builder()
                .tableName(DYNAMO_DB_TABLE_NAME)
                .item(item)
                .build());
        log.info("Data is saved {}", putItemResponse);
    }
}