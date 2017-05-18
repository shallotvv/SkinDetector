package com.vvxc.skindetector.model.impl;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vvxc.skindetector.Api.PhotoService;
import com.vvxc.skindetector.Api.UserService;
import com.vvxc.skindetector.Bean.ImgUrlListBean;
import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.model.AnnalysisModel;
import com.vvxc.skindetector.model.PhotoModel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/5/18.
 */
public class PhotoModelImpl implements PhotoModel {
    @Override
    public void savePhoto(String path, String fileName, Bitmap bm, AnnalysisModel.OnPostCompleteListener listener) {
        File foder = new File(path);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(path,fileName);
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                     /* 采用压缩转档方法 */
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                      /* 调用flush()方法，更新BufferStream */
            bos.flush();

                     /* 结束OutputStream */
            bos.close();
            listener.onSuccess();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void getPhotoList(String method, String session, final OnGetPhotoListListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PhotoService service= retrofit.create(PhotoService.class);

        Call<ImgUrlListBean> call=service.getImgListUrl("JSESSIONID="+session,method);

        call.enqueue(new Callback<ImgUrlListBean>() {
            @Override
            public void onResponse(Call<ImgUrlListBean> call, Response<ImgUrlListBean> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ImgUrlListBean> call, Throwable t) {

            }
        });
    }
}
