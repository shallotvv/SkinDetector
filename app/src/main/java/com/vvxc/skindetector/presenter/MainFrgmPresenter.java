package com.vvxc.skindetector.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.model.MainFrgmModel;
import com.vvxc.skindetector.model.MainFrgmModelImpl;
import com.vvxc.skindetector.view.fragment.MainFragment;
import com.vvxc.skindetector.view.fragment.MainFragmentView;

/**
 * Created by vvxc on 2017/3/12.
 *
 * MainFragmentPresenter
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

    public void getWeather(String string){
        model.getWeather(string, new MainFrgmModel.OnGetWeatherCompeleteListener() {
            @Override
            public void onSuccess(String temperature, String location, String weather, String humidity) {
                getView().setWeather("天气："+weather);
                getView().setTemperature(temperature+"°");
                getView().setLocation(location);
                if (Integer.parseInt(temperature)<16){
                    getView().setTip("护肤小建议:"+Constants.TIP_COLD);
                }else{
                    getView().setTip("护肤小建议:"+Constants.TIP_MIDDLE);
                }
                getView().showSuccess();
            }

            @Override
            public void onFail() {
                getView().showFail();
            }
        });
    }


}
