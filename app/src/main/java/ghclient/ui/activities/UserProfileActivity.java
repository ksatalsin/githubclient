package ghclient.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ghclient.BaseApplication;
import ghclient.R;
import ghclient.di.components.DaggerActivityComponent;
import ghclient.di.modules.ActivityModule;
import ghclient.model.User;
import ghclient.mvp.presenters.impl.UserProfilePresenter;
import ghclient.mvp.views.UserProfileView;

public class UserProfileActivity extends AppCompatActivity implements UserProfileView {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolBarLayout;

    @Inject
    UserProfilePresenter mUserPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = (User) getIntent().getSerializableExtra(UsersActivity.EXTRA_USER);

        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        initializeToolbar();
        initializeDependencyInjector();
        initializePresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserPresenter.onStart();
    }

    private void initializeToolbar() {

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }

    private void initializePresenter() {

        mUserPresenter.initializeView(this);
        mUserPresenter.init();
    }

    private void initializeDependencyInjector() {

        BaseApplication baseApplication = (BaseApplication) getApplication();

        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(baseApplication.getAppComponent())
                .build().inject(this);
    }

    public void showError(String errorMessage) {

        new AlertDialog.Builder(this)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                .setMessage(errorMessage)
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onStop() {

        super.onStop();
        mUserPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserPresenter.onDestroy();
    }

    @Override
    public void showUserDetails(String text) {

    }

    @Override
    public void showAvatar(String url) {

    }

    @Override
    public void showLogin(String name) {

    }
}
