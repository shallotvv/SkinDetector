package com.vvxc.skindetector.model;

import android.util.Log;

import com.vvxc.skindetector.Api.LoginService;
import com.vvxc.skindetector.Bean.UserLoginBean;
import com.vvxc.skindetector.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/3/10.
 */
public class LoginModelImpl implements LoginModel{
    @Override
    public void postUserInfo(UserLoginBean user, final OnPostCompleteListener onPostCompleteListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        LoginService service= retrofit.create(LoginService.class);
        Call<String> call=service.getString(user.getPhone(),user.getPassword());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!=null){
                    onPostCompleteListener.onPostSuccess();
                }else{
                    onPostCompleteListener.onPostFail();
                }
                Log.i("wxc_login",response.body()+"----\n"+response.headers()+"---------\n"+response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
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
