package com.vvxc.skindetector.view.fragment;

/**
 * Created by vvxc on 2017/3/12.
 */
public interface MainFragmentView {
    void setTemperature(String string);
    void setWeather(String string);
    void setTip(String string);
    void setLocation(String string);
    void showFail();
    void showGetWeather();
    void showSuccess();
    void setName(String name);
}
