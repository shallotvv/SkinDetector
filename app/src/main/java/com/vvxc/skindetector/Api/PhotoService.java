package com.vvxc.skindetector.Api;

import com.vvxc.skindetector.Bean.ImgUrlListBean;
import com.vvxc.skindetector.Constants;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by vvxc on 2017/5/18.
 */
public interface PhotoService {
        /**
         * 上传图片
         */
        @Multipart
        @POST(Constants.PHOTO_URL)
        Call<String> uploadImage(@Header("cookie") String session,
                                 @Query("name") String name,
                                 @Part("file\"; filename=\"image.jpg") RequestBody imgs);

        @POST(Constants.PHOTO_URL)
        Call<ImgUrlListBean> getImgListUrl(@Header("cookie") String session,
                                           @Query("method") String method);
}
