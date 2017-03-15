package com.vvxc.skindetector.model;

import android.util.Log;

import com.vvxc.skindetector.Bean.UserSelectInfoBean;

/**
 * Created by vvxc on 2017/3/11.
 */
public class SelectInfoModelImpl implements SelectInfoModel{
    @Override
    public void postUserSelectInfo(UserSelectInfoBean user, OnPostCompleteListener listener) {
        Log.i("wxc_debug",user.getSkinTyoe()+"");
        listener.onSuccess();
    }
}
