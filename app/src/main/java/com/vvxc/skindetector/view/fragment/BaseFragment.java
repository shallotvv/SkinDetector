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
 */
public abstract class BaseFragment<T extends BasePresenter<V>,V> extends Fragment{
    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter=createPresenter();
        presenter.attachView((V)this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    protected abstract T createPresenter();
}
