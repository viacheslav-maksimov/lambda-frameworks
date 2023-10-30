package edu.lambda;

import edu.lambda.data.LambdaRequest;
import edu.lambda.data.LambdaResponse;
import edu.lambda.processing.UserScoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Component
public class LambdaFunction implements Function<Message<LambdaRequest>, Message<LambdaResponse>> {

    private final UserScoreService userScoreService;

    public LambdaFunction(UserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }


    @Override
    public Message<LambdaResponse> apply(Message<LambdaRequest> message) {
        var requestId = Objects.requireNonNull(message.getHeaders().getId(), Strings.EMPTY).toString();
        userScoreService.add(message.getPayload());
        return new GenericMessage<>(new LambdaResponse("Ok", requestId));
    }
}
