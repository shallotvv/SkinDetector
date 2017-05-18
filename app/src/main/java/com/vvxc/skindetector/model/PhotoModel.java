package com.vvxc.skindetector.model;

import android.graphics.Bitmap;

import com.vvxc.skindetector.Bean.ImgUrlListBean;

import java.io.File;
import java.util.List;

/**
 * Created by vvxc on 2017/5/18.
 */
public interface PhotoModel {
    void savePhoto(String path, String fileName, Bitmap bm, AnnalysisModel.OnPostCompleteListener listener);
    void getPhotoList(String method,String session, OnGetPhotoListListener listener);

    interface OnGetPhotoListListener{
        void onSuccess(ImgUrlListBean urlList);
        void onFail();
    }

}
