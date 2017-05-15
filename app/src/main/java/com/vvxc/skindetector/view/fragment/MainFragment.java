package com.vvxc.skindetector.view.fragment;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vvxc.skindetector.Bean.SkinDataListBean;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.model.UserSharePreference;
import com.vvxc.skindetector.presenter.MainFrgmPresenter;
import com.vvxc.skindetector.view.activity.MainActivity;
import com.vvxc.skindetector.view.adapter.BluetoothListAdapter;
import com.vvxc.skindetector.view.adapter.MyViewPagerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 首页主界面fragment
 * 包含天气模块和下面的viewpager
 * Created by vvxc on 2017/3/12.
 */
public class MainFragment extends BaseFragment<MainFrgmPresenter,MainFragmentView>implements MainFragmentView,View.OnClickListener {

    public static final int TYPE_WATER=1;
    public static final int TYPE_OIL=2;
    public static final int TYPE_PH=4;
    public static final int TYPE_TEMPERATURE=8;
    View view;
    TextView temporature;
    TextView city;
    TextView weather;
    TextView tip;


    AnnalysisFragment oilFragment,waterFragment,temperatureFragment,phFragment;
    SuggestFragment suggestFragment;

    TabLayout mTabLayout;
    ViewPager mViewPager;
    Toolbar toolbar;
    CoordinatorLayout coordinatorLayout;
    EditText editCity;
    Button blueToothBtn;
    BluetoothAdapter bluetoothAdapter;
    AlertDialog dialog;
    ProgressDialog progressDialog;
    public final static int MAIN_FRGM_TAG=1;
    public final static int ON_CONNECT_SUCCESS=2;
    public final static int ON_CONNECT_FAIL=3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        initView(inflater,container);
        //显示正在获取天气的Toast
        showGetWeather();
        String city=new UserSharePreference().getCity(getActivity().getSharedPreferences("user",Context.MODE_PRIVATE));
        if ("-1".equals(city)){
            presenter.getWeather(editCity.getText().toString());
        }else{
            editCity.setText(city);
            presenter.getWeather(editCity.getText().toString());
        }



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
        blueToothBtn= (Button) view.findViewById(R.id.btn_bluetooth);


        progressDialog=new ProgressDialog(getActivity(),R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false);
        blueToothBtn.setOnClickListener(this);

        coordinatorLayout= (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);

        //当用户填写完城市地点后，点击其他地方收回小键盘，edittext失去焦点
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

        AnnalysisFrgmFactory factory=new AnnalysisFrgmFactory();
        waterFragment=factory.createAnnalysisFrgm(AnnalysisFrgmFactory.WATER_FRGM);
        oilFragment=factory.createAnnalysisFrgm(AnnalysisFrgmFactory.OIL_FRGM);
        temperatureFragment=factory.createAnnalysisFrgm(AnnalysisFrgmFactory.TEMPORATRY_FRGM);
        phFragment=factory.createAnnalysisFrgm(AnnalysisFrgmFactory.PH_FRGM);
        suggestFragment=new SuggestFragment();

        //fragment里嵌套fragment应该使用getChildFragmentManager，不然有时候frament会加载不出来
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(waterFragment, "水分");//添加Fragment
        viewPagerAdapter.addFragment(oilFragment, "油脂");
        viewPagerAdapter.addFragment(temperatureFragment, "温度");
        viewPagerAdapter.addFragment(phFragment, "PH");
        viewPagerAdapter.addFragment(suggestFragment, "总体评估");
//         viewPagerAdapter.addFragment(new TestFragment(), "油脂");
//        viewPagerAdapter.addFragment(new TestFragment(), "温度");
//        viewPagerAdapter.addFragment(new TestFragment(), "PH");
//        viewPagerAdapter.addFragment(new TestFragment(), "总体评估");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
        mViewPager.setOffscreenPageLimit(5); //设置viewpager预加载数量为5个

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
                    new UserSharePreference().saveCity(getActivity().getSharedPreferences("user",Context.MODE_PRIVATE),editCity.getText().toString());
                    presenter.getCity(editCity.getText().toString());
                }
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            //参数是固定写法
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void setName(String name) {
        MainActivity mainActivity= (MainActivity) getActivity();
        mainActivity.setName(name);
    }

    @Override
    public void showConnectBLTFail() {

        Toast.makeText(getActivity(),"连接蓝牙失败", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        dialog.show();
    }

    @Override
    public void showConnectBLTSuccess() {
        Toast.makeText(getActivity(),"连接蓝牙成功", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        dialog.hide();

    }

    @Override
    public void showConnectBLT() {
        dialog.hide();
        progressDialog.setMessage("正在连接...");
        progressDialog.show();

    }

    @Override
    public void reloadAnnalysisData(int dataType, float data,long deviceId) {
        if (dataType==TYPE_WATER){
            waterFragment.reloadData(data,deviceId,weather.getText().toString(),editCity.getText().toString(),temporature.getText().toString());
        }
        if (dataType==TYPE_OIL){
            oilFragment.reloadData(data,deviceId,weather.getText().toString(),editCity.getText().toString(),temporature.getText().toString());
        }
        if (dataType==TYPE_TEMPERATURE){
            temperatureFragment.reloadData(data,deviceId,weather.getText().toString(),editCity.getText().toString(),temporature.getText().toString());
        }
        if (dataType==TYPE_PH){
            phFragment.reloadData(data,deviceId,weather.getText().toString(),editCity.getText().toString(),temporature.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bluetooth:
                    bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

                    if (bluetoothAdapter.isEnabled()!=true){
                        Toast.makeText(getActivity(),"请先打开蓝牙",Toast.LENGTH_SHORT).show();
                        bluetoothAdapter.enable();
                    }else{
                        showBTList();

                    }

                break;
        }
    }

    private void showBTList() {

        List<BluetoothDevice> list =new ArrayList<BluetoothDevice>();

        Set<BluetoothDevice> set=bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device :
                set) {
            list.add(device);
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
        dialog=builder.setTitle("已配对设备:")
                .setNegativeButton("取消",null)
                .setPositiveButton("搜索设备", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"待完成功能,请先使用手机自带蓝牙配对",Toast.LENGTH_SHORT).show();
                    }
                })
                .setView(R.layout.dialog_bluetooth)
                .show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.primary_dark);
        dialog.setCanceledOnTouchOutside(false);

        ListView listView= (ListView) dialog.findViewById(R.id.bluetooth_list);
        listView.setAdapter(new BluetoothListAdapter(getActivity(),list,presenter));
    }


    @Override
    public void onDestroyView() {
        if (bluetoothAdapter!=null){
            if (bluetoothAdapter.isEnabled()==true){
                bluetoothAdapter.disable();
            }
        }
        super.onDestroyView();
    }
}
