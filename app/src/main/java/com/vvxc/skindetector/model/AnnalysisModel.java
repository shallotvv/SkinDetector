package com.vvxc.skindetector.model;

import com.vvxc.skindetector.Bean.SkinDataListBean;

/**
 * Created by vvxc on 2017/3/27.
 */
public interface AnnalysisModel {
    void postSkinData(String token,SkinDataListBean data,OnPostCompleteListener listener);

    interface  OnPostCompleteListener{
        void onSuccess();
        void onFail();
    }
}
