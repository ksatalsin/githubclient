package ghclient.mvp.presenters;

import ghclient.mvp.views.BaseView;

public interface BasePresenter <V extends BaseView>{

    void init();

    void onStart ();

    void onStop ();

    void onDestroy();

    void initializeView(BaseView v);

}
