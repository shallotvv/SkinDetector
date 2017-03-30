package com.vvxc.skindetector.Api;

import com.vvxc.skindetector.Bean.SkinDataListBean;
import com.vvxc.skindetector.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by vvxc on 2017/3/27.
 */
public interface SkinService {

    @POST(Constants.SKIN_URL)
    Call<String> postSkinData(@Body SkinDataListBean dataList,
                              @Header("cookie") String session );

    @FormUrlEncoded
    @POST(Constants.SKIN_URL)
    Call<SkinDataListBean> getSkinData(@Field("time") long time,
                             @Field("method") String method,
                              @Header("cookie") String session );
}
