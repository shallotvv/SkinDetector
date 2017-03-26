package com.vvxc.skindetector.presenter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.model.MainFrgmModel;
import com.vvxc.skindetector.model.MainFrgmModelImpl;
import com.vvxc.skindetector.view.fragment.MainFragment;
import com.vvxc.skindetector.view.fragment.MainFragmentView;

import java.io.IOException;

/**
 * Created by vvxc on 2017/3/12.
 *
 * MainFragmentPresenter
 * 请求天气数据，更新ui
 */
public class MainFrgmPresenter extends BasePresenter<MainFragmentView>{
    MainFrgmModel model=new MainFrgmModelImpl();

    public void getCity(String city){
        model.getCity(city, new MainFrgmModel.OnGetCityCompeleteListener() {
            @Override
            public void onSuccess(String string) {
                Log.i("wxc_cityid",string);
                getWeather(string);
            }

            @Override
            public void onFail() {

            }
        });
    }

    public void getWeather(final String string){
        model.getWeather(string, new MainFrgmModel.OnGetWeatherCompeleteListener() {
            @Override
            public void onSuccess(String temperature, String location, String weather, String humidity) {
                if (isViewAttached()){
                    getView().setWeather("天气："+weather);
                    getView().setTemperature(temperature+"°");
                    getView().setLocation(location);
                    if (Integer.parseInt(temperature)<16){
                        getView().setTip("护肤小建议:"+Constants.TIP_COLD);
                    }else{
                        getView().setTip("护肤小建议:"+Constants.TIP_MIDDLE);
                    }
                    getView().showSuccess();
                }else{
                    getWeather(string);
                }
            }

            @Override
            public void onFail() {
                getView().showFail();
            }
        });
    }

    public void connectBluetooth(BluetoothDevice device){

            getView().showConnectBLT();

            model.connectBluetooth(device, new MainFrgmModel.OnConnectBTCompeleteListener() {
                @Override
                public void onSuccess() {
                    getView().showConnectBLTSuccess();
                    model.acceptData(new MainFrgmModel.OnRecieveDataListener() {
                        @Override
                        public void onRecieveData(int dataType, float data) {
                            getView().reloadAnnalysisData(dataType,data);
                        }
                    });
                }

                @Override
                public void onFail() {
                    getView().showConnectBLTFail();
                }
            });
    }


}
