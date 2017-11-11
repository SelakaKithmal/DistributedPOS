package com.distributedpos.app.api;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RetrofitProvider {


    public static <T> Observable<T> configureObservable(Observable<T> observable) {
        Observable<T> sharedObservable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).share();
        sharedObservable.subscribe(t -> {
        }, error -> {
        });
        return sharedObservable;
    }
}
