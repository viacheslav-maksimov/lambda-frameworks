
package edu.lambda.config;

import dagger.Component;
import edu.lambda.LambdaFunctionTest;
import edu.lambda.config.module.AppConfigModule;

import javax.inject.Singleton;


@Singleton
@Component(modules = {
        AppConfigModule.class
})
public interface TestAppComponent extends AppComponent {

    void inject(LambdaFunctionTest lambdaFunctionTest);
}
