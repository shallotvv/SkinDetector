package com.vvxc.skindetector.model;

/**
 * Created by vvxc on 2017/3/12.
 */
public interface MainFrgmModel {
    void getCity(String city,OnGetCityCompeleteListener listener);

    void getWeather(String id,OnGetWeatherCompeleteListener listener);

    interface OnGetWeatherCompeleteListener {
        void onSuccess(String temperature,String location,String weather,String humidity);
        void onFail();
    }
    interface OnGetCityCompeleteListener {
        void onSuccess(String id);
        void onFail();
    }

}
