package com.vvxc.skindetector.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.vvxc.skindetector.Api.WeatherService;
import com.vvxc.skindetector.Bean.WeatherBean;
import com.vvxc.skindetector.Bean.WeatherCityBean;
import com.vvxc.skindetector.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/3/12.
 * 先搜索城市，得到城市id
 * 通过城市id获取到对应城市的天气
 *
 * 连接蓝牙，接受数据
 */
public class MainFrgmModelImpl implements MainFrgmModel{
    Handler handler=new Handler();
    BluetoothSocket socket= null;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(WeatherService.WHEATHER_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    WeatherService service= retrofit.create(WeatherService.class);

    @Override
    public void getCity(String city, final OnGetCityCompeleteListener listener) {

        Call<WeatherCityBean> call=service.getCity(WeatherService.APP_KEY,city);
        Log.i("wxc_get_info",WeatherService.APP_KEY+city);
        call.enqueue(new Callback<WeatherCityBean>() {
            @Override
            public void onResponse(Call<WeatherCityBean> call, Response<WeatherCityBean> response) {
                listener.onSuccess(response.body().getResults().get(0).getId());
            }

            @Override
            public void onFailure(Call<WeatherCityBean> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
        });

    }

    @Override
    public void getWeather(String id, final OnGetWeatherCompeleteListener listener) {

        Call<WeatherBean> call=service.getWheather(WeatherService.APP_KEY,id,WeatherService.LANGUAGE,WeatherService.UNIT);
        call.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                if (response.body()!=null){
                    String temporature =response.body().getResults().get(0).getNow().getTemperature();
                    String location =response.body().getResults().get(0).getLocation().getName();
                    String weather =response.body().getResults().get(0).getNow().getText();
                    String humidity =response.body().getResults().get(0).getNow().getHumidity();
                    listener.onSuccess(temporature,location,weather,humidity);
                    Log.i("wxc_weather",temporature+location+weather+humidity+"");
                }else {
                    listener.onFail();
                }


            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                try {
                    listener.onFail();
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
        });
    }

    @Override
    public void connectBluetooth(final BluetoothDevice bluetoothDevice, final OnConnectBTCompeleteListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(Constants.MY_UUID));
                    socket.connect();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            listener.onSuccess();
                        }
                    });
                } catch (IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFail();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void acceptData() {
        new AcceptThead(socket).start();
    }

    class AcceptThead extends Thread{
        BluetoothSocket socket;
        InputStream inputStream;
        public  AcceptThead(BluetoothSocket socket){
            this.socket=socket;
            try {
                this.inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            try {
                while (true){
                    if (socket.isConnected()){
                        byte[] data=new byte[1024];
                        int length;
                        if ((length=inputStream.read(data))>0){
                            byte[] buffer=new byte[length];
                            for (int i=0;i<length;i++){

                                buffer[i]=data[i];
                            }
                            Log.i("wxc_bluetooth","length:"+ buffer.length);
                            for (int i=0;i<buffer.length;i++){
                                byte temp=buffer[i];
                                Log.i("wxc_bluetooth","data:2进制："+ Integer.toBinaryString(temp&0x000000FF)+",10进制:"+Integer.valueOf(temp&0x000000FF));
                            }

                        }
                    }

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


}
