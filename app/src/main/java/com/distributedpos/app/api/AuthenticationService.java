package com.distributedpos.app.api;


import com.distributedpos.app.model.ResponseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface AuthenticationService {

    @GET("Customer/{name}/{nic}/{mobile}/{email}/{payment}/{address1}/{address2}/{city}/{date}")
    Observable<ResponseModel> signUp(@Path("name") String name, @Path("nic") String nic,
                                     @Path("mobile") String mobile, @Path("email") String email,
                                     @Path("payment") String payment, @Path("address1") String address1,
                                     @Path("address2") String address2, @Path("city") String city,
                                     @Path("date") String date);

    @GET("Customer/{mobile}")
    Observable<ResponseModel> userAuth(@Path("mobile") String mobile);

}
