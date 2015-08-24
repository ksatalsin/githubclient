package ghclient.usecases;

import javax.inject.Inject;

import ghclient.model.CoreRepository;
import ghclient.model.User;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetUserUsecase implements Usecase<User> {

    private String mUserName;
    private final CoreRepository mRepository;


    @Inject public GetUserUsecase(String userName, CoreRepository repository) {
        mUserName = userName;
        mRepository = repository;
    }

    @Override
    public Observable<User> execute() {

        return mRepository.getUser(mUserName)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
