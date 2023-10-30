package edu.lambda;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import edu.lambda.data.LambdaRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.adapter.aws.AWSLambdaUtils;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.messaging.support.GenericMessage;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@FunctionalSpringBootTest
public class LambdaFunctionTest {

	@Autowired
	private LambdaFunction function;

	@Test
	public void shouldSaveUserScore() {
		GenericMessage<LambdaRequest> genericMessage = new GenericMessage<>(new LambdaRequest("1", "2"),
				Map.of(AWSLambdaUtils.AWS_CONTEXT, new Context() {
					@Override
					public String getAwsRequestId() {
						return "ctx";
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
				}));

		var lambdaResponse = function.apply(genericMessage);

		assertThat(lambdaResponse.getPayload().result()).isEqualTo("Ok");
		assertThat(lambdaResponse.getPayload().requestId()).isEqualTo("ctx");
	}

}