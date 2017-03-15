package com.vvxc.skindetector.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/3/13.
 */
public class WeatherBean {
    private List<result> results=new ArrayList<result>();

    public List<result> getResults() {
        return results;
    }

    public void setResults(List<result> results) {
        this.results = results;
    }

    public static class result{
        private location location;
        private now now;
        private String last_update;

        public WeatherBean.location getLocation() {
            return location;
        }

        public void setLocation(WeatherBean.location location) {
            this.location = location;
        }

        public WeatherBean.now getNow() {
            return now;
        }

        public void setNow(WeatherBean.now now) {
            this.now = now;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }
    }

    public static class  location{
        private String id;
        private String name;
        private String country;
        private String timezone;
        private String timezone_offset;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public String getTimezone_offset() {
            return timezone_offset;
        }

        public void setTimezone_offset(String timezone_offset) {
            this.timezone_offset = timezone_offset;
        }
    }
    public static class  now{

        private String text;
        private String code;
        private String temperature;
        private String feels_like;
        private String pressure;
        private String humidity;
        private String visibility;
        private String wind_direction;
        private String wind_direction_degree;
        private String wind_speed;
        private String wind_scale;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(String feels_like) {
            this.feels_like = feels_like;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getVisibility() {
            return visibility;
        }

        public void setVisibility(String visibility) {
            this.visibility = visibility;
        }

        public String getWind_direction() {
            return wind_direction;
        }

        public void setWind_direction(String wind_direction) {
            this.wind_direction = wind_direction;
        }

        public String getWind_direction_degree() {
            return wind_direction_degree;
        }

        public void setWind_direction_degree(String wind_direction_degree) {
            this.wind_direction_degree = wind_direction_degree;
        }

        public String getWind_speed() {
            return wind_speed;
        }

        public void setWind_speed(String wind_speed) {
            this.wind_speed = wind_speed;
        }

        public String getWind_scale() {
            return wind_scale;
        }

        public void setWind_scale(String wind_scale) {
            this.wind_scale = wind_scale;
        }
    }


}
