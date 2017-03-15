package com.vvxc.skindetector.util;

import com.vvxc.skindetector.Bean.UserLoginBean;
import com.vvxc.skindetector.Bean.UserSignupBean;

/**
 * Created by vvxc on 2017/3/10.
 * 用于判断用户的登录信息是否符合要求
 */
public class JudgeUserInfoUtil {
    public static final int VALID=-1;
    public static final int PASSWORD_INVALID=0;
    public static final int TELEPHONE_INVALID=1;

    public static final int NAME_INVALID=2;
    public static final int EAMAIL_INVALID=3;
    public static final int REENTER_PASSWORD_INVALID=4;

    String tele;
    String password;
    public JudgeUserInfoUtil(){

    }

    public int judge(UserLoginBean user){
        String tele;
        String password;
        tele=user.getPhone();
        password=user.getPassword();
        if (tele.isEmpty()) {
            return TELEPHONE_INVALID;
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 12) {
            return PASSWORD_INVALID;
        }

        return VALID;
    }

    public int judge(UserSignupBean user) {
        String name, email, tele, password, reEnterPassword;

        name = user.getName();
        email = user.getEmail();
        tele = user.getTele();
        password = user.getPassword();
        reEnterPassword = user.getReEnterPassword();


        if (name.isEmpty()) {
            return NAME_INVALID;
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return EAMAIL_INVALID;
        }

        if (tele.isEmpty() || tele.length() != 11) {
            return TELEPHONE_INVALID;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 12) {
            return PASSWORD_INVALID;
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            return REENTER_PASSWORD_INVALID;
        }

        return VALID;
    }
}
