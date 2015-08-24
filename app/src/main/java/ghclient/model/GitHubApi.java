package ghclient.model;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface GitHubApi {

    @GET("/users")
    Observable<List<User>> getUsers(
            @Query("since") long since,
            @Query("per_page") int perPage);

    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String userName);
}
