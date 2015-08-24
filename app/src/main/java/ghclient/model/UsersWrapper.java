package ghclient.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by youyou on 24.08.2015.
 */
public class UsersWrapper implements Serializable {


        private List<User> users;

        public UsersWrapper(){
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

}
