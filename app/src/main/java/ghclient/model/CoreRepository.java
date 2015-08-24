package ghclient.model;

import java.util.List;
import rx.Observable;

public interface CoreRepository {


    Observable<List<User>> getUsers (long since, int pertPage);
    Observable<User> getUser (String userName);

}
