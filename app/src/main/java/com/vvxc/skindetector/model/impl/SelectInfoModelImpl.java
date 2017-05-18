package com.vvxc.skindetector.model.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vvxc.skindetector.Api.UserService;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Bean.UserLoginBean;
import com.vvxc.skindetector.Bean.UserSelectInfoBean;
import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.model.SelectInfoModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/3/11.
 */
public class SelectInfoModelImpl implements SelectInfoModel {
    @Override
    public void postUserSelectInfo(String token, UserSelectInfoBean user, final OnPostCompleteListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                //加入解析json格式数据的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService service = retrofit.create(UserService.class);
        Call<String> call = service.postSexAndSkin("JSESSIONID="+token,user.getSex(), user.getSkinTyoe(),"setSexAndSkin");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Map<String,String> map=new Gson().fromJson(response.body().toString(),new TypeToken<Map<String,String>>(){}.getType());
                    //state:1,成功。 2，出错，3已经存在用户
                    Log.i("wxc_sex_skin_result:",map.get("result"));
                    String  state=map.get("state");
                    if ("1".equals(state)){
                        Log.i("wxc_sex_skin_state",map.get("state"));
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
                try {
                    listener.onFail();
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}
