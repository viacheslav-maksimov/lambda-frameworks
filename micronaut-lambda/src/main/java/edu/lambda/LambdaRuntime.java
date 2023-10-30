package edu.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.lambda.data.LambdaRequest;
import edu.lambda.data.LambdaResponse;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;

@Slf4j
public class LambdaRuntime extends AbstractMicronautLambdaRuntime<LambdaRequest, LambdaResponse, LambdaRequest, LambdaResponse> {

    public static void main(String[] args) {
        try {
            new LambdaRuntime().run(args);
        } catch (MalformedURLException e) {
            log.error("Start up is failed", e);
        }
    }

    @Override
    @Nullable
    protected RequestHandler<LambdaRequest, LambdaResponse> createRequestHandler(String... args) {
        return new LambdaFunction();
    }
}
