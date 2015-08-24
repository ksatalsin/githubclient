package ghclient.model;

import java.net.SocketTimeoutException;
import java.util.List;
import javax.inject.Inject;
import retrofit.RestAdapter;
import rx.Observable;

public class RestRepository implements CoreRepository {

    private static final String URL_END_POINT = "https://api.github.com";
    private final GitHubApi mGitHubApi;

    @Inject
    public RestRepository() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL_END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        mGitHubApi = restAdapter.create(GitHubApi.class);
    }


    @Override
    public Observable<List<User>> getUsers(long since, int pertPage) {
        return mGitHubApi.getUsers(since, pertPage)
                .retry((attemps, error) -> error instanceof SocketTimeoutException && attemps < 2);
    }

    @Override
    public Observable<User> getUser(String userName) {
         return mGitHubApi.getUser(userName)
                .retry((attemps, error) -> error instanceof SocketTimeoutException && attemps < 2);
    }
}
