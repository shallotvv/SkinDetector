package com.vvxc.skindetector.model;

import android.util.Log;

import com.vvxc.skindetector.Api.WeatherService;
import com.vvxc.skindetector.Bean.WeatherBean;
import com.vvxc.skindetector.Bean.WeatherCityBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vvxc on 2017/3/12.
 */
public class MainFrgmModelImpl implements MainFrgmModel{
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
}
