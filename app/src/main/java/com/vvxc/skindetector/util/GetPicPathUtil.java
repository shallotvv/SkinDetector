package com.vvxc.skindetector.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/5/18.
 */
public class GetPicPathUtil {

    public static List<String> getPicturesInLocal(final String path) {
        List<String> list = new ArrayList<String>();
        File file = new File(path);
        File[] allfiles = file.listFiles();
        if (allfiles == null) {
            return null;
        }
        for(int k = 0; k < allfiles.length; k++) {
            final File fi = allfiles[k];
            if(fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".jpg") ||
                        suffix.toLowerCase().equals(".jpeg") ||
                        suffix.toLowerCase().equals(".bmp") ||
                        suffix.toLowerCase().equals(".png") ||
                        suffix.toLowerCase().equals(".gif") ) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }
}
