package edu.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.lambda.data.LambdaRequest;
import edu.lambda.data.LambdaResponse;
import edu.lambda.processing.UserScoreService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named("mytest")
public class LambdaFunction implements RequestHandler<LambdaRequest, LambdaResponse> {

    @Inject
    UserScoreService userScoreService;

    @Override
    public LambdaResponse handleRequest(LambdaRequest request, Context context) {
        if (request == null) {
            log.warn("No records found in message.");
            return new LambdaResponse("Message is empty", context.getAwsRequestId());
        }
        userScoreService.add(request);
        return new LambdaResponse("Ok", context.getAwsRequestId());
    }
}