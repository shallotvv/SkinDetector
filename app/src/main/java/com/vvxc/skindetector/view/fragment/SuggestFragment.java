package com.vvxc.skindetector.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vvxc.skindetector.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/3/30.
 */
public class SuggestFragment extends Fragment{
    View view;
    RadarChart chart;
    TextView suggestText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.fragment_suggest,container,false);
        initView();
        return view;
    }

    private void initView() {
        chart= (RadarChart) view.findViewById(R.id.radar_chart);
        suggestText= (TextView) view.findViewById(R.id.suggest_text);

        suggestText.setText("应使用具有滋润效果的洗面奶，不要使用皂类进行清洁。" +
                "使用具有滋润效果的营养水进行二次清洁并同时滋润皮肤，根据季节的不同选择保湿的产品乳液及膏霜均可。每1-2周到美容院进行补水的保养，效果更佳。"
        );

        YAxis y=chart.getYAxis();
        y.setLabelCount(4);
        y.setAxisMinimum(0);
        y.setDrawLabels(false);
        XAxis x=chart.getXAxis();
        x.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String result=new String();
                if (value==0) result="水分";
                if (value==1) result="油脂";
                if (value==2) result="温度";
                if (value==3) result="ph";
                return result;
            }
        });
        List<RadarEntry> list=new ArrayList<>();

        list.add(new RadarEntry(4));
        list.add(new RadarEntry(8));
        list.add(new RadarEntry(7));
        list.add(new RadarEntry(5));

        RadarDataSet set1 = new RadarDataSet(list, "最近评分");
        set1.setLineWidth(2f);
        set1.setColor(0xffe51c23);
        set1.setFillColor(0xfff36c60);
        set1.setDrawFilled(true);
        chart.setData(new RadarData(set1));

        Description desc=new Description();
        desc.setText("总体评估");
        chart.setDescription(desc);
    }
}
