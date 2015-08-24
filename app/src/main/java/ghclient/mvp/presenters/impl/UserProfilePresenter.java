package ghclient.mvp.presenters.impl;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;

import javax.inject.Inject;

import ghclient.model.RestRepository;
import ghclient.model.User;
import ghclient.mvp.presenters.BasePresenter;
import ghclient.mvp.views.UserProfileView;
import ghclient.mvp.views.BaseView;
import ghclient.ui.activities.UsersActivity;
import ghclient.usecases.GetUserUsecase;
import rx.Subscription;
import ghclient.usecases.GetUsersUsecase;

public class UserProfilePresenter implements BasePresenter {

    private final Context mContext;
    private final RestRepository mRepository;
    private UserProfileView mUserProfileView;
    private User mUser;
    private GetUserUsecase mUserUsecase;
    private Subscription mUserUsecaseSubscription;


    @Inject
    public UserProfilePresenter(Context activityContext, RestRepository repository) {

        mContext = activityContext;
        mRepository = repository;
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

    @Override
    public void onIntentResult(Intent intent) {
        mUser = (User) intent.getSerializableExtra(UsersActivity.EXTRA_USER);


        mUserProfileView.showAvatar(mUser.getAvatarUrl());

        mUserUsecase = new GetUserUsecase(mUser.getLogin(), mRepository);
        mUserUsecaseSubscription = mUserUsecase.execute().subscribe(
                this::onUserResult,
                this::onError);
    }

    private void onError(Throwable throwable) {

    }

    private void onUserResult(User user) {

        if (user.getName() != null) {
            mUserProfileView.showName(user.getName());
        } else if (user.getLogin() != null) {
            mUserProfileView.showName(user.getLogin());
        }
    }


}
