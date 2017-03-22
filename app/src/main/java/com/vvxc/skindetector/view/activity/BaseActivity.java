package com.vvxc.skindetector.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vvxc.skindetector.presenter.BasePresenter;

/**
 *
 * @param <T> 对应activity的presenter，负责activity和model的通讯
 * @param <V> 对应activity的view接口，声明了这个activity的UI操作
 */

public abstract class BaseActivity<T extends BasePresenter<V>,V> extends AppCompatActivity {
    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=createPresenter();
        presenter.attachView((V) this);
        //

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    protected abstract T createPresenter();
}
