package com.vvxc.skindetector.presenter;

import android.content.SharedPreferences;

import com.vvxc.skindetector.MyApplication;
import com.vvxc.skindetector.model.PersonalModel;
import com.vvxc.skindetector.model.PersonalModelImpl;
import com.vvxc.skindetector.model.UserSharePreference;
import com.vvxc.skindetector.view.activity.PersonalActivity;
import com.vvxc.skindetector.view.activity.PersonalView;

/**
 * Created by vvxc on 2017/3/27.
 */
public class PersonalPresenter extends BasePresenter<PersonalView> {
    PersonalModel model=new PersonalModelImpl();

    public void logout(final SharedPreferences sharedPreferences){
        getView().showProgress();
        String token=new UserSharePreference().getToken(sharedPreferences);
        model.logout(token, new PersonalModel.OnLogoutListener() {
            @Override
            public void onSuccess() {
                new UserSharePreference().deleteToken(sharedPreferences);
                if (isViewAttached()){
                    MyApplication application= (MyApplication) ((PersonalActivity)getView()).getApplication();
                    application.setUserInfo(null);
                    getView().showSuccess();
                    getView().hideProgress();
                    getView().goNextContext();
                }
            }

            @Override
            public void onFail() {
                if (isViewAttached()){
                    getView().showFail();
                    getView().hideProgress();
                }
            }
        });
    }


}
