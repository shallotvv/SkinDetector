package com.vvxc.skindetector.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/5/18.
 */
public class ImgUrlListBean {
    List<Path> urlList=new ArrayList<>();

    public List<Path> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<Path> urlList) {
        this.urlList = urlList;
    }

    public static class Path{
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
