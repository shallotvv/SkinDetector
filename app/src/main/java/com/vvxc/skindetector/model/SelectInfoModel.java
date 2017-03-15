package com.vvxc.skindetector.model;

import com.vvxc.skindetector.Bean.UserSelectInfoBean;

/**
 * Created by vvxc on 2017/3/11.
 */
public interface SelectInfoModel {
    /**
     * 提交用户自己认为的皮肤类型和用户的性别
     * @param user
     * @param listener
     */
    void postUserSelectInfo(UserSelectInfoBean user, OnPostCompleteListener listener);

    interface OnPostCompleteListener{
        void onSuccess();
        void onFail();
    };

}
