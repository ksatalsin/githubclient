package ghclient.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ghclient.BaseApplication;
import ghclient.model.CoreRepository;
import ghclient.model.RestRepository;

@Module
public class AppModule {

    private final BaseApplication mBaseApplication;

    public AppModule(BaseApplication baseApplication) {
        this.mBaseApplication = baseApplication;
    }

    @Provides @Singleton
    BaseApplication provideBaseApplicationContext () {
        return mBaseApplication;
    }

    @Provides @Singleton
    RestRepository provideRestRepository () {
        return new RestRepository();
    }

    @Provides @Singleton
    CoreRepository provideCoreRepository (RestRepository restRepository) {
        return restRepository;
    }
}
