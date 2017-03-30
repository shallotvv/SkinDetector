package com.vvxc.skindetector.view.fragment;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.john.waveview.WaveView;
import com.vvxc.skindetector.Bean.SkinDataListBean;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.MyApplication;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.AnnalysisPresenter;
import com.vvxc.skindetector.view.widget.ChartMarkView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vvxc on 2017/3/11.
 * 嵌套在首页里面的viewpager里的图表fragment，一共有四个
 */
public class AnnalysisFragment extends BaseFragment<AnnalysisPresenter,AnnalysisFragmentView> implements AnnalysisFragmentView {
    private  int fullMarks =20;
    private  String label ="水分";
    private  String unit ="%";

    public  int rightYMax,leftYMax,xMax,rightYMin=0,leftYMin=0,xMin=0;

    private int fragmentType;

    SkinDataListBean skinDataList,saveSkinDataList;//传给服务器的list
    YAxis right,left;
    XAxis xAxis;
    List<Entry> dataList;
    LineChart chart;
    WaveView waveView;
    Handler handler=new Handler();
    TextView testdata;
    Bundle bundle;
    View view;
    ProgressDialog dialog;

    Button uploadBtn;
    int  i;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bundle= getArguments();
        rightYMax=  bundle.getInt("y");
        leftYMax=  bundle.getInt("y");
        xMax=  bundle.getInt("x");
        fragmentType=bundle.getInt("type");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //解决滑动viewpager回来后，oncreateview会被重复调用，图标和波浪会显示不正确的问题
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }

        super.onCreateView(inflater,container,savedInstanceState);

        initView(container);

        return  view;
    }

    @Override
    protected AnnalysisPresenter createPresenter() {
        return new AnnalysisPresenter();
    }

    private void initView(ViewGroup container) {
        view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_annalysis,container,false);
        uploadBtn= (Button) view.findViewById(R.id.btn_upload);
        waveView= (WaveView) view.findViewById(R.id.wave_view);
        chart= (LineChart) view.findViewById(R.id.oil_chart);
        testdata= (TextView) view.findViewById(R.id.test_data);


        skinDataList=new SkinDataListBean();
        saveSkinDataList=new SkinDataListBean();


        dialog=new ProgressDialog(getContext(),R.style.AppTheme_Dark_Dialog);

        Description description=new Description();
        description.setText("");
        chart.setDescription(description);

        dataList =new ArrayList<>();
        right=chart.getAxisRight();
        left=chart.getAxisLeft();
        xAxis=chart.getXAxis();

        right.setDrawGridLines(false);
        right.setDrawLabels(false);
        left.setDrawGridLines(false);
        chart.setNoDataText("暂无数据");
        chart.setNoDataTextColor(0xFFCC1D1D);
        chart.setMarker(new ChartMarkView(getActivity(),R.layout.view_mark));
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        waveView.setProgress(0);

        xAxis.setAxisMaximum(xMax);
        left.setAxisMaximum(leftYMax);
        right.setAxisMaximum(rightYMax);

        xAxis.setAxisMinimum(xMin);
        left.setAxisMinimum(leftYMin);
        right.setAxisMinimum(leftYMax);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication application= (MyApplication) getActivity().getApplication();
                UserInfoBean user=application.getUserInfo();
                if (user==null||user.getUser_name()==null){
                   Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (skinDataList.getSkin_data_list().size()==0){
                    Toast.makeText(getActivity(),"暂无需要更新的数据",Toast.LENGTH_SHORT).show();
                    return;
                }
                saveSkinDataList.setMethod("setSkinData");
                presenter.saveValue(user.getToken(),saveSkinDataList);
            }
        });
    }

    @Override
    public void reloadData(float data) {
        Log.i("wxc_annalysis","更新数据");
        Date date=new Date();
        dataList.add(new Entry(dataList.size(),data,date.getTime()));


        //新测量的数据,要传给服务器的数据
        SkinDataListBean.SkinDataBean skinDataBean=new SkinDataListBean.SkinDataBean();
        skinDataBean.setSkin_date(date.getTime());
        skinDataBean.setSkin_type(fragmentType);
        skinDataBean.setSkin_value(data);
        skinDataList.getSkin_data_list().add(skinDataBean);
        saveSkinDataList.getSkin_data_list().add(skinDataBean);

        LineDataSet lineDataSet=new LineDataSet(dataList,label);
        lineDataSet.setColor(0xFFE43F3F);
        lineDataSet.setCircleColor(0xffCC1D1D);
        LineData lineData=new LineData(lineDataSet);
        chart.setData(lineData);
        chart.invalidate();

        ValueAnimator animator= new ValueAnimator().ofFloat(0,data).setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                int progress;
                if (value< fullMarks) progress= (int) (value*100/ fullMarks);
                else progress=100;
                waveView.setProgress(progress);
                testdata.setText((float)(Math.round(value*10))/10+""+unit);
            }
        });
        animator.start();

    }


    @Override
    public void setFullMarks(int fullMarks) {
        this.fullMarks =fullMarks;
    }

    @Override
    public void setLabel(String label) {
        this.label=label;
    }

    @Override
    public void setUnit(String unit) {
        this.unit=unit;
    }

    @Override
    public void showSuccess() {
        saveSkinDataList.getSkin_data_list().clear();
        Toast.makeText(getActivity(),"上传数据成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        dialog.setMessage("正在上传...");
        dialog.show();
    }

    @Override
    public void showFail() {
        Toast.makeText(getActivity(),"上传失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }


}
