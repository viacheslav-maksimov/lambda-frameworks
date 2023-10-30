package edu.lambda.config;

import dagger.Component;
import edu.lambda.config.module.AppConfigModule;
import edu.lambda.processing.UserScoreService;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppConfigModule.class})
public interface AppComponent {
    UserScoreService getUserScoreService();
}
