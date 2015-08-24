package ghclient.mvp.views;


public interface UserProfileView extends BaseView {

    void showUserDetails (String text);

    void showAvatar (String url);

    void showLogin (String name);

}
