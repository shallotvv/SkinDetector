package com.vvxc.skindetector.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vvxc.skindetector.R;
import com.vvxc.skindetector.view.activity.MainActivity;
import com.vvxc.skindetector.view.adapter.MyViewPagerAdapter;

/**
 * Created by vvxc on 2017/3/29.
 */
public class DataFragment extends Fragment {
    View view;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_data,container,false);
        initView();
        return view;
    }

    private void initView() {
        mTabLayout= (TabLayout) view.findViewById(R.id.data_tabLayout);
        mViewPager= (ViewPager) view.findViewById(R.id.data_viewpager);
        toolbar= (Toolbar) view.findViewById(R.id.data_toolBar);

        toolbar.setTitle("数据分析");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (MainActivity)getActivity()).openDrawer();
            }
        });



        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new TestFragment(), "日");//添加Fragment
        viewPagerAdapter.addFragment(new TestFragment(), "月");
        viewPagerAdapter.addFragment(new TestFragment(), "年");

        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
        mViewPager.setOffscreenPageLimit(5); //设置viewpager预加载数量为5个

        mTabLayout.addTab(mTabLayout.newTab().setText("日"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("月"));
        mTabLayout.addTab(mTabLayout.newTab().setText("年"));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
