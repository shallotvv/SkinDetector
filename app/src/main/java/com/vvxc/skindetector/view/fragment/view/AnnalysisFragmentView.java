package com.vvxc.skindetector.view.fragment.view;

/**
 * Created by vvxc on 2017/3/22.
 */
public interface AnnalysisFragmentView {

    void reloadData(float data,long deviceId,String weather,String address,String temporature);
    void setFullMarks(int fullMarks);

    void setLabel(String label);
    void setUnit(String unit);

    void showSuccess();
    void showDialog();
    void showFail();
    void hideDialog();
}
