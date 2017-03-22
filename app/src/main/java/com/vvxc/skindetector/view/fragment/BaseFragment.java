package com.vvxc.skindetector.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vvxc.skindetector.presenter.BasePresenter;

/**
 * Created by vvxc on 2017/3/12.
 * 泛型T是对应fragment的presenter，负责连接model层和view层
 * V是对应Fragment的接口，接口定义了对应view的一系列ui操作
 */
public abstract class BaseFragment<T extends BasePresenter<V>,V> extends Fragment{
    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    //创建view的时候，创建presenter，并将让presenter持有view的引用
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter=createPresenter();
        presenter.attachView((V)this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    protected abstract T createPresenter();
}
