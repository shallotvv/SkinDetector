package com.vvxc.skindetector.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.vvxc.skindetector.Bean.UserSelectInfoBean;
import com.vvxc.skindetector.model.SelectInfoModel;
import com.vvxc.skindetector.model.impl.SelectInfoModelImpl;
import com.vvxc.skindetector.view.activity.SelectInfoActivity;
import com.vvxc.skindetector.view.activity.view.SelectInfoView;

/**
 * Created by vvxc on 2017/3/11.
 * 提交用户的肤质和性别
 */
public class SelectInfoPresenter extends BasePresenter<SelectInfoView> {
    private SelectInfoModel model=new SelectInfoModelImpl();

    public void SelectInfo(UserSelectInfoBean user){
        SelectInfoActivity activity= (SelectInfoActivity) getView();
        SharedPreferences sharedPreferences=activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        String token=sharedPreferences.getString("token","-1");
        if ("-1".equals(token)){
            getView().showFail();
        }else{
            getView().showDialog();
            model.postUserSelectInfo(token,user, new SelectInfoModel.OnPostCompleteListener() {
                @Override
                public void onSuccess() {
                    getView().hideDialog();
                    getView().goNextContext();
                }

                @Override
                public void onFail() {
                    getView().hideDialog();
                    getView().showFail();
                }
            });
        }

    }


}
