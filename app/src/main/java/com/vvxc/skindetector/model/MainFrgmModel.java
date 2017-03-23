package com.vvxc.skindetector.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.io.IOException;

/**
 * Created by vvxc on 2017/3/12.
 */
public interface MainFrgmModel {
    void getCity(String city,OnGetCityCompeleteListener listener);

    void getWeather(String id,OnGetWeatherCompeleteListener listener);

    void connectBluetooth(BluetoothDevice bluetoothDevice,OnConnectBTCompeleteListener listener);

    void acceptData();

    interface OnGetWeatherCompeleteListener {
        void onSuccess(String temperature,String location,String weather,String humidity);
        void onFail();
    }
    interface OnGetCityCompeleteListener {
        void onSuccess(String id);
        void onFail();
    }
    interface OnConnectBTCompeleteListener {
        void onSuccess();
        void onFail();
    }

}
