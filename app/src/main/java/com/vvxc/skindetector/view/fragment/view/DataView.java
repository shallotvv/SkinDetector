package com.vvxc.skindetector.view.fragment.view;

import com.vvxc.skindetector.Bean.SkinDataListBean;

/**
 * Created by vvxc on 2017/3/29.
 */
public interface DataView {
    void loadData(SkinDataListBean dataList);

    void showSuccess();
    void showDialog();
    void showFail();
    void hideDialog();
}
