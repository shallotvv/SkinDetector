package com.vvxc.skindetector.model.impl;

import android.util.Log;

import com.vvxc.skindetector.Api.UserService;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Bean.UserLoginBean;
import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.model.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/3/10.
 */
public class LoginModelImpl implements LoginModel {
    @Override
    public void postUserInfo(UserLoginBean user, final OnPostCompleteListener onPostCompleteListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                //加入解析json格式数据的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService service= retrofit.create(UserService.class);
        Call<UserInfoBean> call=service.getInfo(user.getPhone(),user.getPassword(),"login");
        call.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                if (response.body()!=null){

                    if (response.body().getState()==1){
                        onPostCompleteListener.onPostSuccess(response.body());
                        Log.i("wxc_login","login\n"+"success");
                        return;
                    }
                    Log.i("wxc_login","login\n"+"fail"+",state:"+response.body().getState());
                }
                onPostCompleteListener.onPostFail();
            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                onPostCompleteListener.onPostFail();
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });


    }
}
