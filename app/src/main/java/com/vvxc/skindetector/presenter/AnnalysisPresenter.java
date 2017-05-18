package com.vvxc.skindetector.presenter;

import com.vvxc.skindetector.Bean.SkinDataListBean;
import com.vvxc.skindetector.model.AnnalysisModel;
import com.vvxc.skindetector.model.impl.AnnalysisModelImpl;
import com.vvxc.skindetector.view.fragment.view.AnnalysisFragmentView;

/**
 * Created by vvxc on 2017/3/27.
 */
public class AnnalysisPresenter extends BasePresenter<AnnalysisFragmentView> {
    AnnalysisModel model=new AnnalysisModelImpl();

    public void saveValue(String token, SkinDataListBean data){
        if (isViewAttached()){
            getView().showDialog();
        }
        model.postSkinData(token, data, new AnnalysisModel.OnPostCompleteListener() {
            @Override
            public void onSuccess() {
                if (isViewAttached()){
                    getView().showSuccess();
                    getView().hideDialog();

                }
            }

            @Override
            public void onFail() {
                if (isViewAttached()){
                    getView().showFail();
                    getView().hideDialog();
                }
            }
        });
    }
}
