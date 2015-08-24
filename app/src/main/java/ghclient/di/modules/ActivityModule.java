package ghclient.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Context mContext;

    public ActivityModule(Context context) {

        this.mContext = context;
    }

    @Provides @PerActivity
    Context provideContext() {
        return mContext;
    }
}
