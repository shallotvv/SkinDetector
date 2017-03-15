package com.vvxc.skindetector.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by vvxc on 2017/3/10.
 */
public class BasePresenter<T> {
    /**
     * presenter要通知View层更新ui，必须持有View层的引用
     */
    protected Reference<T> getView;


    public void attachView(T view){
        this.getView =new WeakReference<T>(view);
    }

    protected T getView(){
        return getView.get(); //获取View
    }
    public boolean isViewAttached(){
        return getView != null && getView.get() != null; //判断是否与View建立关联
    }
    public void detachView(){
        if(getView != null){
            getView.clear(); //解除关联
            getView = null;
        }
    }
}
