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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
                if (response.body().getResults().size()!=0){
                    listener.onSuccess(response.body().getResults().get(0).getId());
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<WeatherCityBean> call, Throwable t) {
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
                    Log.i("wxc_weather",temporature+location+weather+humidity+"");
                    listener.onSuccess(temporature,location,weather,humidity);
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
    public void acceptData(OnRecieveDataListener listener) {
        new AcceptThead(socket,listener).start();
    }

    class AcceptThead extends Thread{
        BluetoothSocket socket;
        InputStream inputStream;
        OnRecieveDataListener listener;
        public  AcceptThead(BluetoothSocket socket,OnRecieveDataListener listener){
            this.listener=listener;
            this.socket=socket;
            try {
                this.inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            List<Byte> result=new ArrayList<Byte>();

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
                            //先传过来0xff，然后传数据
                            if (Integer.valueOf(buffer[0]&0x000000FF)==255) {
                                Log.i("wxc_bluetooth", "接受到0xff，开始收数据");
                                result.clear();
                            }
                                for (int i=0;i<buffer.length;i++){
                                    byte temp=buffer[i];
                                    Log.i("wxc_bluetooth","2进制："+ Integer.toBinaryString(temp&0x000000FF)+",10进制:"+Integer.valueOf(temp&0x000000FF));

                                    result.add(temp);
                                    if (result.size()>=11&&i==(buffer.length-1)){
                                        Log.i("wxc_bluetooth","result9:"+Integer.valueOf(result.get(9)&0x000000FF)+"result10:"+Integer.valueOf(result.get(10)&0x000000FF));
                                        final int resultType=Integer.valueOf(result.get(9)&0x000000FF);
                                        final float resultData;
                                        if (resultType==8){
                                            if(result.size()>11) resultData= (float) (Integer.valueOf(result.get(10)&0x000000FF)+Integer.valueOf(0xFF & result.get(11))/10.0);
                                            else resultData= (float) Integer.valueOf(result.get(10)&0x000000FF);
                                        }else if (resultType==4){
                                            resultData= (float) (Integer.valueOf(result.get(10)&0x000000FF)/10.0);
                                        }else{

                                            resultData=Integer.valueOf(result.get(10)&0x000000FF);
                                        }

                                        long deviceId=0;
                                        for (int j = 1; j < 9; j++) {
                                            deviceId <<= 8;
                                            deviceId += result.get(i) ;
                                        }

                                        final long finalDeviceId = Math.abs(deviceId);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i("wxc_bluetooth","final的数据,result9:"+resultType+"result10:"+resultData);
                                                listener.onRecieveData(resultType,resultData, finalDeviceId);
                                            }
                                        });

                                    }
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
