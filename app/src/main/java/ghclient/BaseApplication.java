package ghclient;

import android.app.Application;
import ghclient.di.components.AppComponent;
import ghclient.di.components.DaggerAppComponent;
import ghclient.di.modules.AppModule;


public class BaseApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {

        mAppComponent = DaggerAppComponent.builder()
            .appModule(new AppModule(this))
            .build();
    }

    public AppComponent getAppComponent() {

        return mAppComponent;
    }
}
