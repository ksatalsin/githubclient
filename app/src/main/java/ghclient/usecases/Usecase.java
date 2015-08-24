package ghclient.usecases;

import rx.Observable;

public interface Usecase<T> {

    Observable<T> execute();
}
