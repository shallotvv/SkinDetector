package com.vvxc.skindetector.view.fragment;

/**
 * Created by vvxc on 2017/3/12.
 * mainfragment的UI操作，修改天气，地点，护肤建议等
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

    void showConnectBLTFail();
    void showConnectBLTSuccess();
    void showConnectBLT();

    void reloadAnnalysisData(int dataType,float data,long deviceId);
}
