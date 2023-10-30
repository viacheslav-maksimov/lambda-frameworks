package edu.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import edu.lambda.data.LambdaRequest;
import io.micronaut.context.ApplicationContext;
import io.micronaut.function.aws.runtime.RuntimeContext;
import io.micronaut.function.aws.test.annotation.MicronautLambdaTest;
import io.micronaut.test.annotation.MockBean;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

@MicronautLambdaTest
@Slf4j
public class LambdaRequestHandlerITest {

    @Inject
    private ApplicationContext applicationContext;
    @Inject
    DynamoDbClient dynamoDbClient;
    @MockBean(DynamoDbClient.class)
    DynamoDbClient dynamoDbClient(){
        return mock(DynamoDbClient.class);
    }
    @Inject
    public Context context;
    @MockBean(RuntimeContext.class)
    RuntimeContext context(){
        return mock(RuntimeContext.class);
    }

    @Test
    void shouldExecute() {
        LambdaFunction sampleRequestHandler = new LambdaFunction(applicationContext);

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("userId", AttributeValue.builder().n("1").build());
        item.put("score", AttributeValue.builder().n("5").build());
        Mockito.when(dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName("UserScore")
                .item(item)
                .build())).thenReturn(PutItemResponse.builder().build());
        Mockito.when(context.getAwsRequestId()).thenReturn("aws-id");

        var response = sampleRequestHandler.execute(new LambdaRequest("1", "5"));

        Assertions.assertEquals("Ok", response.result());
    }
}
