package edu.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.lambda.config.AppComponent;
import edu.lambda.config.DaggerAppComponent;
import edu.lambda.data.LambdaRequest;
import edu.lambda.data.LambdaResponse;
import edu.lambda.processing.UserScoreService;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;

@Log4j2
public class LambdaFunction implements RequestHandler<LambdaRequest, LambdaResponse> {

    private final UserScoreService userScoreService;

    public LambdaFunction() {
        this(DaggerAppComponent.create());
    }

    @Inject
    LambdaFunction(AppComponent component) {
        this.userScoreService = component.getUserScoreService();
    }

    @Override
    public LambdaResponse handleRequest(LambdaRequest request, Context context) {
        log.info("Processing lambda");
        if (request == null) {
            log.warn("No records found in message.");
            return new LambdaResponse("Message is empty", context.getAwsRequestId());
        }
        userScoreService.add(request);
        return new LambdaResponse("Ok", context.getAwsRequestId());
    }
}

