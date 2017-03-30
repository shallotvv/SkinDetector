package com.vvxc.skindetector.Bean;

import java.io.Serializable;

/**
 * Created by vvxc on 2017/3/21.
 */
public class UserInfoBean implements Serializable{
    private int user_id;
    private String user_email;
    private String user_name;
    private String user_sex;
    private String user_tel;
    private String user_skin_type;

    public String getUser_skin_type() {
        return user_skin_type;
    }

    public void setUser_skin_type(String user_skin_type) {
        this.user_skin_type = user_skin_type;
    }

    private String token;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }
}
