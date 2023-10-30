package edu.lambda;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import edu.lambda.config.DaggerAppComponent;
import edu.lambda.data.LambdaRequest;
import org.junit.jupiter.api.Test;

public class LambdaFunctionTest {

    private LambdaFunction lambdaFunction;

    @Test
    void handleRequestWithValidation() {
    var com = DaggerAppComponent.create();
        this.lambdaFunction = new LambdaFunction(com);
        lambdaFunction.handleRequest(new LambdaRequest("1", "1"), new Context() {
            @Override
            public String getAwsRequestId() {
                return null;
            }

            @Override
            public String getLogGroupName() {
                return null;
            }

            @Override
            public String getLogStreamName() {
                return null;
            }

            @Override
            public String getFunctionName() {
                return null;
            }

            @Override
            public String getFunctionVersion() {
                return null;
            }

            @Override
            public String getInvokedFunctionArn() {
                return null;
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return null;
            }
        });
    }

//    @BeforeEach
//    public void setUp() {
//        TestAppComponent component = DaggerTestAppComponent.create();
//        component.inject(this);
//        lambdaFunction = new LambdaFunction(component);
//    }
//
//    @Test
//    void handleRequestWithValidation() {
//        var event = new SQSEvent();
//        var message = new SQSEvent.SQSMessage();
//        message.setMessageId("123");
//        message.setBody(EventMessage.getBody(OperationTypes.VALIDATION, "1", "1"));
//        event.setRecords(List.of(message));
//        var result = lambdaFunction.handleRequest(event, new MockLambdaContext());
//        assertThat(result.getBatchItemFailures(), hasSize(0));
//    }
//
//    @Test
//    void handleRequestWithProcessing() {
//        var event = new SQSEvent();
//        var message = new SQSEvent.SQSMessage();
//        message.setMessageId("123");
//        message.setBody(EventMessage.getBody(OperationTypes.PROCESSING, "1", "1"));
//        event.setRecords(List.of(message));
//        var result = lambdaFunction.handleRequest(event, new MockLambdaContext());
//        assertThat(result.getBatchItemFailures(), hasSize(0));
//    }

}
