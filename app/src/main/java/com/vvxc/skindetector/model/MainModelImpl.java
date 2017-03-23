package com.vvxc.skindetector.model;

import android.util.Log;

import com.vvxc.skindetector.Api.UserService;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/3/21.
 */
public class MainModelImpl implements  MainModel {
    @Override
    public void postToken(String token, final OnPostTokenCompleteListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService service= retrofit.create(UserService.class);
        Call<UserInfoBean> call=service.getInfoByToken("JSESSIONID="+token+"; Path=/SkinDectector/; ",token,"token");
        call.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                if (response.body()!=null){
                    if (response.body().getState()==1){

                        Log.i("wxc_login","------state-----\n"+response.body().getState()+"\n----");
                        listener.onSuccess(response.body());
                        Log.i("wxc_login","login\n"+"success");
                        return;
                    }

                }
                Log.i("wxc_login","login\n"+"fail,state:"+response.body().getState());
                listener.onFail();

            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                listener.onFail();
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}
