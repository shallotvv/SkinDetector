package com.vvxc.skindetector.Api;

import com.vvxc.skindetector.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by vvxc on 2017/3/10.
 */
public interface LoginService {
    @FormUrlEncoded
    @POST(Constants.LoginUrl)
    Call<String> getString(@Field("tel") String phone,
                           @Field("password") String password,
                           @Field("method") String method);


}
