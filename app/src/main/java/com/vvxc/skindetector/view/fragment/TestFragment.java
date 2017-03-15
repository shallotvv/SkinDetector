package com.vvxc.skindetector.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.vvxc.skindetector.Bean.ChartDataBean;
import com.vvxc.skindetector.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/3/11.
 */
public class TestFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_oil,container,false);
        LineChart chart = (LineChart) view.findViewById(R.id.oil_chart);
        ChartDataBean dataBean=new ChartDataBean();
        dataBean.setValueX(5);
        dataBean.setValueY(6);

        List<Entry> data=new ArrayList<Entry>();

        data.add(new Entry(5,6));
        data.add(new Entry(6,7));
        data.add(new Entry(7,8));
        data.add(new Entry(8,9));
        data.add(new Entry(9,10));
        data.add(new Entry(10,11));
        data.add(new Entry(11,12));
        data.add(new Entry(12,13));
        LineDataSet lineDataSet=new LineDataSet(data,"油脂");
        lineDataSet.setColor(0xFFE43F3F);
        lineDataSet.setDrawCircles(false);
        LineData lineData=new LineData(lineDataSet);
        chart.setData(lineData);

        XAxis xAxis=chart.getXAxis();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



        return  view;
    }
}
