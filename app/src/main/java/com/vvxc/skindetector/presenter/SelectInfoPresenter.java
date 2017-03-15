package com.vvxc.skindetector.presenter;

import com.vvxc.skindetector.Bean.UserSelectInfoBean;
import com.vvxc.skindetector.model.SelectInfoModel;
import com.vvxc.skindetector.model.SelectInfoModelImpl;
import com.vvxc.skindetector.view.activity.SelectInfoView;

/**
 * Created by vvxc on 2017/3/11.
 */
public class SelectInfoPresenter extends BasePresenter<SelectInfoView> {
    private SelectInfoModel model=new SelectInfoModelImpl();

    public void SelectInfo(UserSelectInfoBean user){
        getView().showDialog();
        model.postUserSelectInfo(user, new SelectInfoModel.OnPostCompleteListener() {
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
