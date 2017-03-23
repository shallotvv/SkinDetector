package com.vvxc.skindetector.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.vvxc.skindetector.Bean.ChartDataBean;
import com.vvxc.skindetector.R;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by vvxc on 2017/3/11.
 */
public class AnnalysisFragment extends Fragment implements AnnalysisFragmentView {

    List<Entry> data;
    LineChart chart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_annalysis,container,false);
         chart= (LineChart) view.findViewById(R.id.oil_chart);


        data=new ArrayList<>();
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

    @Override
    public void reloadData(String string) {
        Log.i("wxc_annalysis","更新数据");
        data.add(new Entry(13,5));
        LineDataSet lineDataSet= (LineDataSet) chart.getLineData().getDataSetByIndex(0);
        lineDataSet.notifyDataSetChanged();
  //      chart.notifyDataSetChanged();

        chart.setData(new LineData(lineDataSet));
        chart.invalidate();
    }
}
