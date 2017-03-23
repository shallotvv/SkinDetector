package com.vvxc.skindetector.presenter;

import com.vvxc.skindetector.Bean.UserSignupBean;
import com.vvxc.skindetector.model.SignupModel;
import com.vvxc.skindetector.model.SignupModelImpl;
import com.vvxc.skindetector.util.JudgeUserInfoUtil;
import com.vvxc.skindetector.view.activity.SignupView;

/**
 * Created by vvxc on 2017/3/10.
 */
public class SignupPresenter extends BasePresenter<SignupView> {
    private SignupModel signupModel=new SignupModelImpl();
    public void signup(UserSignupBean user){
        JudgeUserInfoUtil util=new JudgeUserInfoUtil();
        int type=-1;
        type=util.judge(user);

        if (JudgeUserInfoUtil.NAME_INVALID==type){
            getView().showNameInvalid();
            return;
        }

        if (JudgeUserInfoUtil.EAMAIL_INVALID==type){
            getView().showEmailInvalid();
            return;
        }

        if (JudgeUserInfoUtil.TELEPHONE_INVALID==type){
            getView().showTeleInvalid();
            return;
        }

        if (JudgeUserInfoUtil.PASSWORD_INVALID==type){
            getView().showPasswordInvalid();
            return;
        }
        if (JudgeUserInfoUtil.REENTER_PASSWORD_INVALID==type){
            getView().showReEnterInvalid();
            return;
        }

        getView().showDialog();
        user.setMethod("signup");
        signupModel.postUserInfo(user, new SignupModel.OnPostCompleteListener() {
            @Override
            public void onSuccess() {
                getView().hideDialog();
                getView().goNextContext();
            }

            @Override
            public void onFail(String state) {
                getView().hideDialog();
                //state:1,成功。 2，出错，3已经存在用户
                getView().showSignupFail(state);
            }
        });

    }
}
