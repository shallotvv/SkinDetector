package com.vvxc.skindetector.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.MainFrgmPresenter;
import com.vvxc.skindetector.view.activity.MainActivity;
import com.vvxc.skindetector.view.adapter.MyViewPagerAdapter;

/**
 * Created by vvxc on 2017/3/12.
 */
public class MainFragment extends BaseFragment<MainFrgmPresenter,MainFragmentView>implements MainFragmentView {
    View view;
    TextView temporature;
    TextView city;
    TextView weather;
    TextView tip;

    TabLayout mTabLayout;
    ViewPager mViewPager;
    Toolbar toolbar;
    CoordinatorLayout coordinatorLayout;
    EditText editCity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        initView(inflater,container);
        showGetWeather();
        presenter.getWeather(editCity.getText().toString());

        return view;
    }

    @Override
    protected MainFrgmPresenter createPresenter() {
        return new MainFrgmPresenter();
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {

        view=inflater.inflate(R.layout.fragment_main,container,false);

        city= (TextView) view.findViewById(R.id.edit_city);
        weather= (TextView) view.findViewById(R.id.weather);
        tip= (TextView) view.findViewById(R.id.weather_tip);
        temporature= (TextView) view.findViewById(R.id.temperature);
        editCity= (EditText) view.findViewById(R.id.edit_city);
        toolbar= (Toolbar) view.findViewById(R.id.toolBar);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        coordinatorLayout= (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        coordinatorLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                coordinatorLayout.setFocusable(true);
                coordinatorLayout.setFocusableInTouchMode(true);
                coordinatorLayout.requestFocus();
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editCity.getWindowToken(), 0);
                return false;
            }
        });
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (MainActivity)getActivity()).openDrawer();
            }
        });



        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(((MainActivity)getActivity()).getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TestFragment(), "水分");//添加Fragment
        viewPagerAdapter.addFragment(new TestFragment(), "油脂");
        viewPagerAdapter.addFragment(new TestFragment(), "温度");
        viewPagerAdapter.addFragment(new TestFragment(), "PH");
        viewPagerAdapter.addFragment(new TestFragment(), "总体评估");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器

        mTabLayout.addTab(mTabLayout.newTab().setText("水分"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("油脂"));
        mTabLayout.addTab(mTabLayout.newTab().setText("温度"));
        mTabLayout.addTab(mTabLayout.newTab().setText("PH"));
        mTabLayout.addTab(mTabLayout.newTab().setText("总体评估"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题



        editCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (false==hasFocus){
                    //getcity里调用了getWeather方法
                    showGetWeather();
                    presenter.getCity(editCity.getText().toString());
                }
            }
        });

    }

    @Override
    public void setTemperature(String string) {
        temporature.setText(string);
    }

    @Override
    public void setWeather(String string) {
        weather.setText(string);

    }

    @Override
    public void setTip(String string) {
        tip.setText(string);

    }

    @Override
    public void setLocation(String string) {
        editCity.setText(string);
    }

    @Override
    public void showFail() {
        Toast.makeText(getActivity(),"无法获取到对应城市的天气",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGetWeather() {
        Toast.makeText(getActivity(),"正在获取对应城市的天气...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {

        Toast.makeText(getActivity(),"获取天气成功.",Toast.LENGTH_SHORT).show();
    }
}
