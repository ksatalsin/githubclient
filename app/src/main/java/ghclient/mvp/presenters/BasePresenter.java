package ghclient.mvp.presenters;

import android.content.Intent;

import ghclient.mvp.views.BaseView;

public interface BasePresenter<V extends BaseView> {

    void init();

    void onStart();

    void onStop();

    void onDestroy();

    void initializeView(BaseView v);

    void onIntentResult(Intent intent);
}
