package com.vvxc.skindetector.Bean;

/**
 * Created by vvxc on 2017/3/10.
 */
public class UserSignupBean {
    private String method;
    private String user_name;
    private String user_email;
    private String user_tel;
    private String user_password;
    private String reEnterPassword;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return user_name;
    }

    public void setName(String name) {
        this.user_name = name;
    }

    public String getEmail() {
        return user_email;
    }

    public void setEmail(String email) {
        this.user_email = email;
    }

    public String getTele() {
        return user_tel;
    }

    public void setTele(String tele) {
        this.user_tel = tele;
    }

    public String getPassword() {
        return user_password;
    }

    public void setPassword(String password) {
        this.user_password = password;
    }

    public String getReEnterPassword() {
        return reEnterPassword;
    }

    public void setReEnterPassword(String reEnterPassword) {
        this.reEnterPassword = reEnterPassword;
    }
}
