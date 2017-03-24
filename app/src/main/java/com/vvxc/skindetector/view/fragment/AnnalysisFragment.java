package com.vvxc.skindetector.view.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.john.waveview.WaveView;
import com.vvxc.skindetector.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/3/11.
 */
public class AnnalysisFragment extends Fragment implements AnnalysisFragmentView {


    List<Entry> dataList;
    LineChart chart;
    WaveView waveView;
    Handler handler=new Handler();
    TextView testdata;
    View view;
    int  i;

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

        Log.i("wxc_annalysis","oncreateview");
        view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_annalysis,container,false);
        waveView= (WaveView) view.findViewById(R.id.wave_view);
        chart= (LineChart) view.findViewById(R.id.oil_chart);
        testdata= (TextView) view.findViewById(R.id.test_data);
        dataList =new ArrayList<>();


        YAxis right=chart.getAxisRight();
        YAxis left=chart.getAxisLeft();
        right.setAxisMaximum(100);
        right.setAxisMinimum(0);
        right.setDrawGridLines(false);
        right.setDrawLabels(false);
        left.setAxisMaximum(100);
        left.setAxisMinimum(0);
        left.setDrawGridLines(false);

        chart.setNoDataText("暂无数据");
        chart.setNoDataTextColor(0xFFCC1D1D);
        chart.getAxisLeft().setDrawGridLines(false);

        XAxis xAxis=chart.getXAxis();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(20);
        xAxis.setAxisMinimum(0);
        waveView.setProgress(0);
        return  view;
    }

    @Override
    public void reloadData(int data) {
        Log.i("wxc_annalysis","更新数据");

        dataList.add(new Entry(dataList.size(),data));
        LineDataSet lineDataSet=new LineDataSet(dataList,"水分");
        lineDataSet.setColor(0xFFE43F3F);
        lineDataSet.setCircleColor(0xffCC1D1D);
        LineData lineData=new LineData(lineDataSet);
        chart.setData(lineData);
        chart.setData(new LineData(lineDataSet));
        chart.invalidate();

        ValueAnimator animator= new ValueAnimator().ofInt(0,data).setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (int) animation.getAnimatedValue();
                int progress;
                if (value<20) progress=value*100/20;
                else progress=100;
                waveView.setProgress(progress);
                testdata.setText(value+"%");
            }
        });
        animator.start();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("wxc_annalysis","oncreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.i("wxc_annalysis","onattach");
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        Log.i("wxc_annalysis","onstart");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.i("wxc_annalysis","onpause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("wxc_annalysis","onstaop");
        super.onStop();
    }

    @Override
    public void onResume() {

        Log.i("wxc_annalysis","onresume");
        super.onResume();
    }
}
