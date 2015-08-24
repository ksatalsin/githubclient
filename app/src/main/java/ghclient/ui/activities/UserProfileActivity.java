package ghclient.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import ghclient.BaseApplication;
import ghclient.R;
import ghclient.di.components.DaggerActivityComponent;
import ghclient.di.modules.ActivityModule;
import ghclient.mvp.presenters.impl.UserProfilePresenter;
import ghclient.mvp.views.UserProfileView;

public class UserProfileActivity extends AppCompatActivity implements UserProfileView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.iv_avatar)
    ImageView mAvatar;

    @Bind(R.id.tv_name)
    TextView mName;

    @Inject
    UserProfilePresenter mUserPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        initializeToolbar();
        initializeDependencyInjector();
        initializePresenter();
        initializeIntent();
    }

    private void initializeIntent() {
        mUserPresenter.onIntentResult(getIntent());
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
    }

    private void initializeDependencyInjector() {

        BaseApplication baseApplication = (BaseApplication) getApplication();

        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(baseApplication.getAppComponent())
                .build().inject(this);
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
    public void showAvatar(String url) {

        Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.images_holder)
                .crossFade()
                .into(mAvatar);
    }

    @Override
    public void showName(String name) {
        mName.setText(name);
    }

}
