package ghclient.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ghclient.BaseApplication;
import ghclient.di.modules.AppModule;
import ghclient.model.CoreRepository;

@Singleton @Component(modules = AppModule.class)
public interface AppComponent {

    BaseApplication app();
    CoreRepository dataRepository();
}
