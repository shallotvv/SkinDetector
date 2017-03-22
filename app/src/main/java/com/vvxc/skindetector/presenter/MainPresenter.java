package com.vvxc.skindetector.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.model.MainModel;
import com.vvxc.skindetector.model.MainModelImpl;
import com.vvxc.skindetector.model.UserSharePreference;
import com.vvxc.skindetector.view.activity.MainActivity;
import com.vvxc.skindetector.view.activity.MainView;

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
                    Log.i("wxc_login_by_token","success");
                    getView().setName(user.getUser_name());
                    getView().setLogin(true);
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
