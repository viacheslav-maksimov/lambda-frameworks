package edu.lambda.config.module;

import dagger.Module;
import dagger.Provides;
import edu.lambda.config.AppConfig;
import edu.lambda.config.AppConfigLoader;

import java.net.http.HttpClient;
import java.time.Duration;

@Module
public class AppConfigModule {
    @Provides
    public static AppConfig provideAppConfig() {
        return AppConfigLoader.get();
    }

    @Provides
    public static HttpClient provideHttpClient(AppConfig appConfig) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(appConfig.getHttpClientConfiguration().getRequestTimeOutInSec()))
                .build();
    }
}
