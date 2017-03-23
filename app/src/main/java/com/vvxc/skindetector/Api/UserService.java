package com.vvxc.skindetector.Api;

import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Bean.UserSignupBean;
import com.vvxc.skindetector.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by vvxc on 2017/3/10.
 * 登录接口
 */
public interface UserService {
    @FormUrlEncoded
    @POST(Constants.USER_URL)
    Call<UserInfoBean> getInfo(@Field("tel") String phone,
                               @Field("password") String password,
                               @Field("method") String method);

    @FormUrlEncoded
    @POST(Constants.USER_URL)
    Call<UserInfoBean> getInfoByToken(@Header("cookie") String session,
                                      @Field("token") String token,
                                      @Field("method") String method);

    @POST(Constants.USER_URL)
    Call<String> signup(@Body UserSignupBean user);


}
