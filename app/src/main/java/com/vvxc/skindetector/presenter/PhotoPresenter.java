package com.vvxc.skindetector.presenter;

import android.graphics.Bitmap;

import com.vvxc.skindetector.model.AnnalysisModel;
import com.vvxc.skindetector.model.PhotoModel;
import com.vvxc.skindetector.model.impl.PhotoModelImpl;
import com.vvxc.skindetector.view.fragment.view.PhotoFragmentView;

/**
 * Created by vvxc on 2017/5/18.
 */
public class PhotoPresenter extends BasePresenter<PhotoFragmentView> {
    PhotoModel photoModel=new PhotoModelImpl();

    public  void savePhoto(String path, String fileName, Bitmap bm){
        photoModel.savePhoto(path, fileName, bm, new AnnalysisModel.OnPostCompleteListener() {
            @Override
            public void onSuccess() {
                getView().refreshRecycler();
            }

            @Override
            public void onFail() {

            }
        });

    }

}
