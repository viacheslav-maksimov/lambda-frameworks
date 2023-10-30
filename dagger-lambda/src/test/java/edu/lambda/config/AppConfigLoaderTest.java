package edu.lambda.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppConfigLoaderTest {

    @Test
    void get() {
        var config = AppConfigLoader.get();
        assertNotNull(config);
        assertNotNull(config.getHttpClientConfiguration());
    }
}
