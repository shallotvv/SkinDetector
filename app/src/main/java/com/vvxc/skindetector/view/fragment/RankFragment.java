package com.vvxc.skindetector.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.view.activity.MainActivity;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvxc on 2017/3/29.
 */
public class RankFragment extends Fragment {
    BarChart barChart;
    View view;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_rank,container,false);
        initView();
        initData();
        return view;
    }

    private void initData() {

        List<BarEntry> entryList=new ArrayList<>();
        entryList.add(new BarEntry(0,70));
        entryList.add(new BarEntry(3,50));
        entryList.add(new BarEntry(6,40));
        entryList.add(new BarEntry(9,35));
        entryList.add(new BarEntry(12,20));
        BarDataSet dataSet=new BarDataSet(entryList,"热度");

        List<BarEntry> entryList2=new ArrayList<>();
        entryList2.add(new BarEntry(1,60));
        entryList2.add(new BarEntry(4,65));
        entryList2.add(new BarEntry(7,30));
        entryList2.add(new BarEntry(10,45));
        entryList2.add(new BarEntry(13,40));

        BarDataSet dataSet2=new BarDataSet(entryList2,"用户得分");
        dataSet.setColor(0xffE43F3F);
        dataSet2.setColor(0xffffc107);
        List<IBarDataSet> setList=new ArrayList<>();
        setList.add(dataSet);
        setList.add(dataSet2);
        BarData bardata=new BarData(setList);
        bardata.setBarWidth(1f);
        bardata.groupBars(-1f,1f,0);
        bardata.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                return value+"";
            }
        });
        barChart.setData(bardata);

    }

    private void initView() {
        barChart= (BarChart) view.findViewById(R.id.rank_chart);
        toolbar= (Toolbar) view.findViewById(R.id.rank_toolBar);
        toolbar.setTitle("榜单");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (MainActivity)getActivity()).openDrawer();
            }
        });


        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        Description desc=new Description();
        desc.setText("");
        barChart.setDescription(desc);
        YAxis right=barChart.getAxisRight();
        YAxis left=barChart.getAxisLeft();
        XAxis xAxis=barChart.getXAxis();
        right.setDrawGridLines(false);
        right.setDrawLabels(false);
        right.setDrawAxisLine(false);
        left.setDrawGridLines(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(15);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String result=new String();
                if (value<=0)  result="兰蔻";
                else if (value<=3) result="雅思兰黛";
                else  if (value<=6) result="资生堂";
                else  if(value<=9) result="SK-II";
                else  if (value<=12) result="倩碧";
                return result;
            }
        });


    }
}
