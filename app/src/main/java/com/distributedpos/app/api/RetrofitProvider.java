package com.distributedpos.app.api;


import android.content.Context;

import com.distributedpos.app.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    public static Retrofit createRetrofit(Context context) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    NetworkMonitor monitor = new NetworkMonitor(context);
                    if (!monitor.isConnected()) {
                        throw new NoNetworkException();
                    }
                    return chain.proceed(chain.request());
                }).readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(ApiCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }


    public static <T> Observable<T> configureObservable(Observable<T> observable) {
        Observable<T> sharedObservable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).share();
        sharedObservable.subscribe(t -> {
        }, error -> {
        });
        return sharedObservable;
    }
}
