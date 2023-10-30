package edu.lambda;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class LambdaHandlerTest {

    @InjectMock
    DynamoDbClient dynamoDbClient;

    @Test
    void testProcessingLambdaSuccess() {
        // you test your lambdas by invoking on http://localhost:8081
        // this works in dev mode too
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("userId", AttributeValue.builder().n("1").build());
        item.put("score", AttributeValue.builder().n("5").build());

        Mockito.when(dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName("UserScore")
                .item(item)
                .build())).thenReturn(PutItemResponse.builder().build());
        given()
                .contentType("application/json")
                .accept("application/json")
                .body("{\n" +
                        "      \"userId\": \"1\",\n" +
                        "      \"score\": \"5\"" +
                        "}")
                .when()
                .post()
                .then()
                .statusCode(200);
    }

}
