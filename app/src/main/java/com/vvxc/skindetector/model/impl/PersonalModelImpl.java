package com.vvxc.skindetector.model.impl;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vvxc.skindetector.Api.UserService;
import com.vvxc.skindetector.Api.WeatherService;
import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.model.PersonalModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/3/27.
 */
public class PersonalModelImpl implements PersonalModel {
    @Override
    public void logout(String token, final OnLogoutListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService service= retrofit.create(UserService.class);

        Call<String> call=service.logout("JSESSIONID="+token,"logout");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Map<String,String> map=new Gson().fromJson(response.body().toString(),new TypeToken<Map<String,String>>(){}.getType());
                    //state:1,成功。 2，出错
                    Log.i("wxc_logout:",map.get("result"));
                    String  state=map.get("state");
                    if ("1".equals(state)){
                        Log.i("wxc_logout_state",map.get("state"));
                        listener.onSuccess();
                        return;
                    }
                    if ("0".equals(state)){

                        Log.i("wxc_sex_skin","失败："+state);
                        listener.onFail();
                        return;
                    }
                    listener.onFail();
                    return;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onFail();
            }
        });


    }
}
