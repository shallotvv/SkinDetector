package com.vvxc.skindetector.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by vvxc on 2017/3/10.
 *
 * presenter层负责连接model层和view层
 * 在model层做数据请求，view层完成UI操作
 *
 * 比如用户点击了登录按钮，将数据发送到服务端的工作由model层进行，服务端返回成功后，presenter通知activity更新ui界面
 */
public class BasePresenter<T> {
    /**
     * presenter要通知View层更新ui，必须持有View层的引用
     */
    protected T view;


    public void attachView(T view){
        this.view =view;
    }

    protected T getView(){
        return view; //获取View
    }
    public boolean isViewAttached(){
        return view != null; //判断是否与View建立关联
    }
    public void detachView(){
        if(view != null){
            //解除关联
            view = null;
        }
    }
}
