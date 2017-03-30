package com.vvxc.skindetector.model;

import com.vvxc.skindetector.Bean.SkinDataListBean;

/**
 * Created by vvxc on 2017/3/29.
 */
public interface DataModel {
    void getDataByDay(String token,long time,OnGetDataListener listener);

    interface  OnGetDataListener{
        void onSuccess(SkinDataListBean dataList);
        void onFail();
    }
}
