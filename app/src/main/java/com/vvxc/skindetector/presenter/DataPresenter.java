package com.vvxc.skindetector.presenter;

import com.vvxc.skindetector.Bean.SkinDataListBean;
import com.vvxc.skindetector.model.DataModel;
import com.vvxc.skindetector.model.DataModelImpl;
import com.vvxc.skindetector.view.fragment.DataView;

/**
 * Created by vvxc on 2017/3/29.
 */
public class DataPresenter extends BasePresenter<DataView> {
    DataModel model=new DataModelImpl();

    public void getDataByDay(String token,long time){
        if (isViewAttached()){
            getView().showDialog();
        }
        model.getDataByDay(token, time, new DataModel.OnGetDataListener() {
            @Override
            public void onSuccess(SkinDataListBean dataList) {

                if (isViewAttached()) {
                    getView().showSuccess();
                    getView().hideDialog();
                    getView().loadData(dataList);
                }
            }

            @Override
            public void onFail() {

                if (isViewAttached()) {
                    getView().hideDialog();
                    getView().showFail();
                }
            }
        });
    }
}
