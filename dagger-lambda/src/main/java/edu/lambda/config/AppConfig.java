package edu.lambda.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Data
@Log4j2
public class AppConfig {
    private HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration();
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HttpClientConfiguration {
        private Integer requestTimeOutInSec = 10;
    }
}
