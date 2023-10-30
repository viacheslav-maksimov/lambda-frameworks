package edu.lambda.data;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record LambdaRequest(String userId, String score) {
}
//
//public class LambdaRequest {
//    private final String userId;
//    private final String score;
//
//    @JsonCreator
//    public LambdaRequest(String userId, String score) {
//        this.userId = userId;
//        this.score = score;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public String getScore() {
//        return score;
//    }
//}
