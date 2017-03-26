package com.vvxc.skindetector.view.fragment;

import android.animation.ValueAnimator;
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
import com.john.waveview.WaveView;
import com.vvxc.skindetector.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/3/11.
 */
public class AnnalysisFragment extends Fragment implements AnnalysisFragmentView {
    private  int fullMarks =20;
    private  String label ="水分";
    private  String unit ="%";

    public  int rightYMax,leftYMax,xMax,rightYMin=0,leftYMin=0,xMin=0;

    YAxis right,left;
    XAxis xAxis;
    List<Entry> dataList;
    LineChart chart;
    WaveView waveView;
    Handler handler=new Handler();
    TextView testdata;
    Bundle bundle;
    View view;
    int  i;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bundle= getArguments();
        rightYMax=  bundle.getInt("y");
        leftYMax=  bundle.getInt("y");
        xMax=  bundle.getInt("x");
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

        initView(container);

        return  view;
    }

    private void initView(ViewGroup container) {
        view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_annalysis,container,false);
        waveView= (WaveView) view.findViewById(R.id.wave_view);
        chart= (LineChart) view.findViewById(R.id.oil_chart);
        testdata= (TextView) view.findViewById(R.id.test_data);

        Description description=new Description();
        description.setText("次数");
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

    }

    @Override
    public void reloadData(float data) {
        Log.i("wxc_annalysis","更新数据");
        dataList.add(new Entry(dataList.size(),data));
        LineDataSet lineDataSet=new LineDataSet(dataList,label);
        lineDataSet.setColor(0xFFE43F3F);
        lineDataSet.setCircleColor(0xffCC1D1D);
        LineData lineData=new LineData(lineDataSet);
        chart.setData(lineData);
        chart.setData(new LineData(lineDataSet));
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


}
