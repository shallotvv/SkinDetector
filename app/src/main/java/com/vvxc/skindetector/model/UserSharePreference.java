package com.vvxc.skindetector.model;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by vvxc on 2017/3/21.
 */
public class UserSharePreference {
    public void saveToken(String token,SharedPreferences sharedPreferences){

        Log.i("wxc_share_preference","保存token");
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public String getToken(SharedPreferences sharedPreferences){
        String token=sharedPreferences.getString("token","-1");
        Log.i("wxc_share_preference","读token:"+token);
        return token;
    }

    public boolean deleteToken(SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("token");
        return editor.commit();
    }
    public void saveCity(SharedPreferences sharedPreferences,String city){
        Log.i("wxc_share_preference","保存city");
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("city",city);
        editor.commit();
    }
    public String getCity(SharedPreferences sharedPreferences){
        String city=sharedPreferences.getString("city","-1");
        Log.i("wxc_share_preference","读city:"+city);
        return city;
    }
}
