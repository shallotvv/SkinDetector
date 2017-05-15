package com.vvxc.skindetector.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/3/27.
 */
public class SkinDataListBean {

    private List<SkinDataBean> skin_data_list=new ArrayList<SkinDataBean>();
    private String method;

    public List<SkinDataBean> getSkin_data_list() {
        return skin_data_list;
    }

    public void setSkin_data_list(List<SkinDataBean> skin_data_list) {
        this.skin_data_list = skin_data_list;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public static class SkinDataBean{

        private int skin_type;
        private float skin_value;
        private long skin_date;
        private String skin_address;
        private String skin_machine;
        private String skin_weather;
        private String skin_temperature;

        public String getSkin_temperature() {
            return skin_temperature;
        }

        public void setSkin_temperature(String skin_temperature) {
            this.skin_temperature = skin_temperature;
        }

        public String getSkin_machine() {
            return skin_machine;
        }

        public void setSkin_machine(String skin_machine) {
            this.skin_machine = skin_machine;
        }

        public String getSkin_weather() {
            return skin_weather;
        }

        public void setSkin_weather(String skin_weather) {
            this.skin_weather = skin_weather;
        }

        public String getSkin_address() {
            return skin_address;
        }

        public void setSkin_address(String skin_address) {
            this.skin_address = skin_address;
        }

        public int getSkin_type() {
            return skin_type;
        }

        public void setSkin_type(int skin_type) {
            this.skin_type = skin_type;
        }

        public float getSkin_value() {
            return skin_value;
        }

        public void setSkin_value(float skin_value) {
            this.skin_value = skin_value;
        }

        public long getSkin_date() {
            return skin_date;
        }

        public void setSkin_date(long skin_date) {
            this.skin_date = skin_date;
        }
    }
}
