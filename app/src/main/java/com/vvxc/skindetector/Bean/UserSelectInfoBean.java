package com.vvxc.skindetector.Bean;

/**
 * Created by vvxc on 2017/3/11.
 * 皮肤类型和性别
 * 0：未知
 * 1：中性
 * 2：油性
 * 3：干性
 * 4：敏感性
 * 5：混合性
 *
 * 1：男 0：女
 */
public class UserSelectInfoBean {
    private int skinTyoe;
    private int sex;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSkinTyoe() {
        return skinTyoe;
    }

    public void setSkinTyoe(int skinTyoe) {
        this.skinTyoe = skinTyoe;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

}
