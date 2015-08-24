package ghclient.mvp.views;

import ghclient.model.User;

public interface UsersView extends BaseView {

    void addUser(User user);
    void startLoader();
    void stopLoader();
    void removeUsers();
}
