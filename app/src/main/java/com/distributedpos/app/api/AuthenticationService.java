package com.distributedpos.app.api;


import com.distributedpos.app.model.ResponseModel;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthenticationService {

    @POST("user/authenticate")
    Observable<ResponseModel> userAuth(@Query("order") String order);

}
