
package edu.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import edu.lambda.data.LambdaRequest;
import edu.lambda.data.LambdaResponse;
import edu.lambda.processing.UserScoreService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanProvider;
import io.micronaut.context.annotation.Any;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LambdaFunction extends MicronautRequestHandler<LambdaRequest, LambdaResponse> {

    @Inject
    UserScoreService userScoreService;
    @Any
    public BeanProvider<Context> context;
    public LambdaFunction() {
        super();
    }

    public LambdaFunction(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    public LambdaResponse execute(LambdaRequest request) {
        if (request == null) {
            log.warn("No records found in message.");
            return new LambdaResponse("Message is empty", getContext().getAwsRequestId());
        }
        userScoreService.add(request);
        return new LambdaResponse("Ok", getContext().getAwsRequestId());
    }

    Context getContext() {
        return context.get();
    }
}

