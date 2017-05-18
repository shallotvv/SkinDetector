package com.vvxc.skindetector.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.MyApplication;
import com.vvxc.skindetector.model.MainModel;
import com.vvxc.skindetector.model.impl.MainModelImpl;
import com.vvxc.skindetector.model.UserSharePreference;
import com.vvxc.skindetector.view.activity.MainActivity;
import com.vvxc.skindetector.view.activity.view.MainView;

/**
 * Created by vvxc on 2017/3/21.
 * 通过保存在本地的token登录，登录成功后更新ui
 */
public class MainPresenter extends BasePresenter<MainView> {
    MainModel mainModel=new MainModelImpl();
    public void loginByToken(SharedPreferences sharedPreferences){
        String token=new UserSharePreference().getToken(sharedPreferences);
        //过去token，有则返回，没有时默认值是-1
        if ("-1".equals(token)){
            Log.i("wxc_login_by_token","没有token");
            return;
        }else{
            mainModel.postToken(token, new MainModel.OnPostTokenCompleteListener() {
                @Override
                public void onSuccess(UserInfoBean user) {
                    getView().setName(user.getUser_name());
                    getView().setLogin(true);
                    MyApplication application= (MyApplication) ((MainActivity)getView()).getApplication();
                    application.setUserInfo(user);
                }

                @Override
                public void onFail() {
                    getView().setLogin(false);
                    Log.i("wxc_login_by_token","fail");
                }
            });
        }
    }



}
