package com.vvxc.skindetector.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.vvxc.skindetector.Api.UserService;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Bean.UserSignupBean;
import com.vvxc.skindetector.Constants;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/3/10.
 */
public class SignupModelImpl implements SignupModel {
    @Override
    public void postUserInfo(UserSignupBean user, final OnPostCompleteListener onPostCompleteListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                //加入解析json格式数据的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService service = retrofit.create(UserService.class);
        Call<String> call = service.signup(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("wxc_signup",response.body().toString());
                Map<String,String> map=new Gson().fromJson(response.body().toString(),new TypeToken<Map<String,String>>(){}.getType());
                //state:1,成功。 2，出错，3已经存在用户
                Log.i("wxc_signupp",map.get("result"));
                String  state=map.get("state");
                if ("1".equals(state)){
                    onPostCompleteListener.onSuccess();
                    return;
                }
                if ("2".equals(state)||"3".equals(state)){

                    Log.i("wxc_signupp","失败："+state);
                    onPostCompleteListener.onFail(state);
                    return;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                onPostCompleteListener.onFail("4");
            }
        });
    }
}
