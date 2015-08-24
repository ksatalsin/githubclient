package ghclient.usecases;

import java.util.List;
import javax.inject.Inject;
import ghclient.model.User;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ghclient.model.CoreRepository;

public class GetUsersUsecase implements Usecase<List<User>> {

    private final CoreRepository mRepository;
    private long mSince;
    private int mPerPage;

    @Inject public GetUsersUsecase(long since, int perPage, CoreRepository repository) {

        mSince = since;
        mPerPage = perPage;
        mRepository = repository;
    }

    @Override
    public Observable<List<User>> execute() {

        return mRepository.getUsers(mSince,mPerPage)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
