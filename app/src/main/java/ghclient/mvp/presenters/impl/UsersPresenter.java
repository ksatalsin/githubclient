package ghclient.mvp.presenters.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.colintmiller.simplenosql.NoSQL;
import com.colintmiller.simplenosql.NoSQLEntity;
import com.colintmiller.simplenosql.RetrievalCallback;

import java.util.List;

import javax.inject.Inject;

import ghclient.model.RestRepository;
import ghclient.model.User;
import ghclient.model.UsersWrapper;
import ghclient.mvp.presenters.BasePresenter;
import ghclient.mvp.views.UsersView;
import ghclient.mvp.views.BaseView;
import ghclient.ui.listeners.RecyclerOnReachedEndListener;
import ghclient.usecases.GetUserUsecase;
import ghclient.usecases.GetUsersUsecase;
import ghclient.ui.listeners.RecyclerClickListener;
import ghclient.ui.activities.UserProfileActivity;
import ghclient.ui.activities.UsersActivity;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UsersPresenter implements BasePresenter, RecyclerClickListener, RecyclerOnReachedEndListener {

    private static final int OFFSET = 40;
    private static final String TAG = "GC";
    private static String USER_TABLE = "USER_TABLE";
    private static String USERS_ENTITY = "USERS_ENTITY";
    private final Context mContext;
    private RestRepository mRepository;
    private List<User> mUsersList;
    private UsersView mUserView;
    private GetUserUsecase mUserUsecase;
    private GetUsersUsecase mUsersUsecase;
    private long mSinece = 3285995;
    private Subscription mGetUsersSubscription;
    private Subscription mUserUsecaseSubscription;
    private UsersWrapper usersWrapper = new UsersWrapper();

    @Inject
    public UsersPresenter(Context context, RestRepository repository) {

        mContext = context;
        mRepository = repository;
    }

    @Override
    public void init() {

        showCatched();
    }

    private void showCatched() {

        NoSQL.with(mContext)
                .using(UsersWrapper.class)
                .bucketId(USER_TABLE)
                .entityId(USERS_ENTITY)
                .retrieve((RetrievalCallback<UsersWrapper>) noSQLEntities -> {

                    UsersWrapper usersWrapper1 = null;
                    try {
                        usersWrapper1 = noSQLEntities.get(0).getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (usersWrapper1 != null && usersWrapper1.getUsers() != null) {
                        onUsersRecived(usersWrapper1.getUsers());
                    } else {
                        loadUsers();
                    }

                });
    }

    @Override
    public void onStart() {
    }

    @Override
    public void initializeView(BaseView v) {

        mUserView = (UsersView) v;
    }

    @Override
    public void onIntentResult(Intent intent) {

    }

    private void loadUsers() {
        mUserView.startLoader();
        mUsersUsecase = new GetUsersUsecase(mSinece, OFFSET, mRepository);
        mGetUsersSubscription = mUsersUsecase.execute()
                .subscribe(
                        this::onUsersRecived,
                        this::onError);
    }

    private void onUsersRecived(List<User> users) {
        Log.e(TAG, "onUsersRecived: ");

        mUserView.stopLoader();
        usersWrapper.setUsers(users);

        NoSQLEntity<UsersWrapper> entity = new NoSQLEntity<UsersWrapper>(USER_TABLE, USERS_ENTITY);
        entity.setData(usersWrapper);

        NoSQL.with(mContext).using(UsersWrapper.class).save(entity);

        Observable.from(users)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadUser,
                        this::onError);
    }

    private void loadUser(User user) {
        /* mUserUsecase = new GetUserUsecase(user.getLogin(),mRepository);

       mUserUsecaseSubscription = mUserUsecase.execute().subscribe(
                this::onUserResult,
                this::onError);*/

        onUserResult(user);
    }


    private void onUserResult(User user) {

        mUserView.addUser(user);
    }

    private void onError(Throwable error) {
        Log.e(TAG, "erro: " + error.getLocalizedMessage());

        mUserView.stopLoader();
    }


    @Override
    public void onItemClick(User user) {

        Intent i = new Intent(mContext, UserProfileActivity.class);
        i.putExtra(UsersActivity.EXTRA_USER, user);
        mContext.startActivity(i);
    }

    @Override
    public void onItemClickUrl(User user) {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(user.getHtmlUrl()));
        mContext.startActivity(i);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (mGetUsersSubscription != null && !mGetUsersSubscription.isUnsubscribed()) {
            mGetUsersSubscription.unsubscribe();
        }

        if (mUserUsecaseSubscription != null && !mUserUsecaseSubscription.isUnsubscribed()) {
            mUserUsecaseSubscription.unsubscribe();
        }
    }


    @Override
    public void onReachedEnd(User user) {

        mSinece = user.getId();
        loadUsers();
    }

    public void onRefresh() {
        mSinece = 3285995;
        mUserView.removeUsers();

        NoSQL.with(mContext)
                .using(UsersWrapper.class)
                .bucketId(USER_TABLE)
                .entityId(USERS_ENTITY).delete();

        loadUsers();
    }
}
