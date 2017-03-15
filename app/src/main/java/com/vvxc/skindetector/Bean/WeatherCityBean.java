package com.vvxc.skindetector.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/3/13.
 */
public class WeatherCityBean {
    public List<WeatherCityDetailBean> getResults() {
        return results;
    }

    public void setResults(List<WeatherCityDetailBean> results) {
        this.results = results;
    }

    private List<WeatherCityDetailBean> results=new ArrayList<WeatherCityDetailBean>();

    public static class WeatherCityDetailBean {
        private String id;
        private String name;
        private String country;
        private String path;
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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
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

}
