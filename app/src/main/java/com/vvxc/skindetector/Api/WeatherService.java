package com.vvxc.skindetector.Api;

import com.vvxc.skindetector.Bean.WeatherBean;
import com.vvxc.skindetector.Bean.WeatherCityBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vvxc on 2017/3/12.
 * 获取天气接口
 */
public interface WeatherService {
    static final String WHEATHER_URL="weather/now.json";
    static final String LOCATION_URL="location/search.json";


    static final String WHEATHER_BASE_URL="https://api.thinkpage.cn/v3/";
    static final String APP_KEY="t8uhd5ah0p5p1llc";
    static final String LANGUAGE="zh-Hans";
    static final String UNIT="c";



    @GET(WHEATHER_URL)
    Call<WeatherBean> getWheather(@Query("key") String key,
                                  @Query("location") String location,
                                  @Query("language") String language,
                                  @Query("unit") String unit);

    @GET(LOCATION_URL)
    Call<WeatherCityBean> getCity(@Query("key") String key,
                                  @Query("q") String q);
}
