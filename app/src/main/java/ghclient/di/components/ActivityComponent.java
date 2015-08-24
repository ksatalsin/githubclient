package ghclient.di.components;


import android.content.Context;

import dagger.Component;
import ghclient.di.modules.PerActivity;
import ghclient.di.modules.ActivityModule;
import ghclient.ui.activities.UserProfileActivity;
import ghclient.ui.activities.UsersActivity;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(UsersActivity activity);
    void inject(UserProfileActivity activity);
    Context context();
}
