package com.vvxc.skindetector.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vvxc.skindetector.Api.SkinService;
import com.vvxc.skindetector.Api.UserService;
import com.vvxc.skindetector.Bean.SkinDataListBean;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Constants;

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
public class AnnalysisModelImpl implements AnnalysisModel {
    @Override
    public void postSkinData(String token, SkinDataListBean data, final OnPostCompleteListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                //加入解析json格式数据的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SkinService service= retrofit.create(SkinService.class);
        Call<String> call =service.postSkinData(data,"JSESSIONID="+token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Map<String,String> map=new Gson().fromJson(response.body().toString(),new TypeToken<Map<String,String>>(){}.getType());
                    //state:1,成功。 2，出错
                    if(map==null){
                        listener.onFail(); return;
                    }
                    Log.i("wxc_save:",map.get("result"));
                    String  state=map.get("state");
                    if ("1".equals(state)){
                        Log.i("wxc_save_state",map.get("state"));
                        listener.onSuccess();
                        return;
                    }
                    if ("0".equals(state)){

                        Log.i("wxc_save","失败："+state);
                        listener.onFail();
                        return;
                    }
                }
                listener.onFail();
                return;
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onFail();
            }
        });
    }
}
