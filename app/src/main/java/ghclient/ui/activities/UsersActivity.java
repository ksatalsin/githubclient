package ghclient.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ghclient.BaseApplication;

import ghclient.R;
import ghclient.di.components.DaggerActivityComponent;
import ghclient.di.modules.ActivityModule;
import ghclient.model.User;
import ghclient.mvp.presenters.impl.UsersPresenter;
import ghclient.mvp.views.UsersView;
import ghclient.ui.adapter.UsersAdapter;

public class UsersActivity extends AppCompatActivity
        implements UsersView, SwipeRefreshLayout.OnRefreshListener {

    public final static String EXTRA_USER = "user";

    @Bind(R.id.progress)
    ProgressBar mProgress;
    @Bind(R.id.rv_users)
    RecyclerView mUsersRecycler;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeToRefresh;

    @Inject
    UsersPresenter mUsersPresenter;
    private UsersAdapter mUsersAdapter;
    private List<User> mUsers = new ArrayList<User>();
    private LinearLayoutManager mLinearLayoutManager;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;
    private boolean loading = true;
    private int previousTotal;
    private int visibleThreshold = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        initializeToolbar();

        initializeSwipeToRefreshView();
        initializeDependencyInjector();
        initializePresenter();

        initializeRecyclerView();
    }

    private void initializeSwipeToRefreshView() {

        mSwipeToRefresh.setOnRefreshListener(this);
    }

    private void initializeToolbar() {

        setSupportActionBar(mToolbar);
    }


    @Override
    protected void onStart() {

        super.onStart();
        mUsersPresenter.onStart();
    }

    private void initializePresenter() {

        mUsersPresenter.initializeView(this);
        mUsersPresenter.init();
    }

    private void initializeDependencyInjector() {

        BaseApplication baseApplication = (BaseApplication) getApplication();
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(baseApplication.getAppComponent())
                .build().inject(this);
    }

    private void initializeRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mUsersRecycler.setLayoutManager(mLinearLayoutManager);

        mUsersAdapter = new UsersAdapter(mUsers, this, mUsersPresenter);
        mUsersRecycler.setAdapter(mUsersAdapter);

        mUsersRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mUsersRecycler.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {

                    Log.i("ghc", "end called");

                    mUsersPresenter.onReachedEnd(mUsersAdapter.getItem(mUsersAdapter.getItemCount() - 1));
                    loading = true;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUsersPresenter.onStop();
    }


    @Override
    public void addUser(User user) {

        mUsersAdapter.addItem(user);
        mUsersAdapter.notifyDataSetChanged();
    }

    @Override
    public void startLoader() {
        loading = true;
        mProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void stopLoader() {
        loading = false;
        mProgress.setVisibility(View.GONE);

        if(mSwipeToRefresh.isRefreshing()){
            mSwipeToRefresh.setRefreshing(false);
        }
    }

    @Override
    public void removeUsers() {
        mUsersAdapter.removeItems();
        mUsersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mUsersPresenter.onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUsersPresenter.onDestroy();
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
