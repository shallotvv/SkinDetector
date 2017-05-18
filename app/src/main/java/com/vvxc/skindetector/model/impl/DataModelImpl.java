package com.vvxc.skindetector.model.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.vvxc.skindetector.Api.SkinService;
import com.vvxc.skindetector.Bean.SkinDataListBean;
import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.model.DataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/3/29.
 */
public class DataModelImpl implements DataModel {
    @Override
    public void getDataByDay(String token, long time, final OnGetDataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                //加入解析json格式数据的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SkinService service= retrofit.create(SkinService.class);
        Call<SkinDataListBean> call =service.getSkinData(time,"getDataByDay","JSESSIONID="+token);
        call.enqueue(new Callback<SkinDataListBean>() {
            @Override
            public void onResponse(Call<SkinDataListBean> call, Response<SkinDataListBean> response) {
//                Log.i("wxc_skin_data",response.body().getSkin_data_list().get(0).getSkin_value()+"");

                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SkinDataListBean> call, Throwable t) {

            }
        });
    }
}
