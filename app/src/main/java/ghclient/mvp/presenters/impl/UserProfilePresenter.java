package ghclient.mvp.presenters.impl;
import android.content.Context;
import android.widget.AdapterView;

import javax.inject.Inject;

import ghclient.model.User;
import ghclient.mvp.presenters.BasePresenter;
import ghclient.mvp.views.UserProfileView;
import ghclient.mvp.views.BaseView;
import rx.Subscription;
import ghclient.usecases.GetUsersUsecase;

public class UserProfilePresenter implements BasePresenter {

    private final Context mActivityContext;
    private UserProfileView mUserProfileView;


    @Inject
    public UserProfilePresenter(Context activityContext) {

        mActivityContext = activityContext;
    }

    @Override
    public void init() {

    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void initializeView(BaseView v) {

        mUserProfileView = (UserProfileView) v;
    }

}
