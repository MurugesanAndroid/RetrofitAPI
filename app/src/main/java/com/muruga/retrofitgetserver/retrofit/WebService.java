package com.muruga.retrofitgetserver.retrofit;

import com.muruga.retrofitgetserver.models.Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WebService {

    @FormUrlEncoded
    @POST("push/send_verification_code")
    Call<Model> callGetService();

    @FormUrlEncoded
    @POST("push/send_verification_code")
    Call<Model> callSendService(@Field("email") String email,
                                @Field("password") String password);

}