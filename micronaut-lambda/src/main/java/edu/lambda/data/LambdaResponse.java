package edu.lambda.data;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record LambdaResponse(String result, String requestId) {
}
//public class LambdaResponse {
//    private final String result;
//    private final String requestId;
//
//    public LambdaResponse(String result, String requestId) {
//        this.result = result;
//        this.requestId = requestId;
//    }
//
//    public String getResult() {
//        return result;
//    }
//}
