package com.vvxc.skindetector.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.vvxc.skindetector.Bean.SkinDataListBean;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.MyApplication;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.DataPresenter;
import com.vvxc.skindetector.view.activity.MainActivity;
import com.vvxc.skindetector.view.fragment.view.DataView;
import com.vvxc.skindetector.view.widget.ChartMarkView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vvxc on 2017/3/29.
 */
public class DataFragment extends BaseFragment<DataPresenter,DataView>  implements DataView {
    UserInfoBean user;
    ProgressDialog dialog;
    View view;
    LineChart chart;
    Toolbar toolbar;
    EditText timeEdit;
    Button timeBtn;
    YAxis right,left;
    XAxis xAxis;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view=inflater.inflate(R.layout.fragment_data_annalysis,container,false);
        initView();
        initChart();
        return view;
    }

    private void initChart() {

        Description description=new Description();
        description.setText("");
        chart.setDescription(description);

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
        left.setAxisMaximum(100);
        right.setAxisMaximum(100);
    }

    @Override
    protected DataPresenter createPresenter() {
        return new DataPresenter();
    }

    private void initView() {
        final MyApplication apl= (MyApplication) getActivity().getApplication();
        user=apl.getUserInfo();
        dialog=new ProgressDialog(getContext(),R.style.AppTheme_Dark_Dialog);
        chart= (LineChart) view.findViewById(R.id.data_chart);
        toolbar= (Toolbar) view.findViewById(R.id.data_toolBar);
        timeBtn= (Button) view.findViewById(R.id.btn_time);
        timeEdit= (EditText) view.findViewById(R.id.edit_time);

        toolbar.setTitle("数据分析");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (MainActivity)getActivity()).openDrawer();
            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=apl.getUserInfo();
                if (user==null){
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }

                String timeStr=timeEdit.getText().toString();
                if (!"".equals(timeStr)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    Date date= null;
                    try {
                        date = sdf.parse(timeStr);
                        presenter.getDataByDay(user.getToken(),date.getTime());
                    } catch (ParseException e) {
                        Toast.makeText(getActivity(),"格式错误",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity(),"格式错误",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    @Override
    public void loadData(SkinDataListBean dataList) {
        ArrayList<SkinDataListBean.SkinDataBean> waterList=new ArrayList<>();
        ArrayList<SkinDataListBean.SkinDataBean> oilList=new ArrayList<>();
        ArrayList<SkinDataListBean.SkinDataBean> tprtList=new ArrayList<>();
        ArrayList<SkinDataListBean.SkinDataBean> phList=new ArrayList<>();

        for (int i=0;i<dataList.getSkin_data_list().size();i++){
            SkinDataListBean.SkinDataBean bean;
            bean=dataList.getSkin_data_list().get(i);
            if (bean.getSkin_type()== AnnalysisFrgmFactory.WATER_FRGM) waterList.add(bean);
            if (bean.getSkin_type()==AnnalysisFrgmFactory.OIL_FRGM) oilList.add(bean);
            if (bean.getSkin_type()==AnnalysisFrgmFactory.TEMPORATRY_FRGM) tprtList.add(bean);
            if (bean.getSkin_type()==AnnalysisFrgmFactory.PH_FRGM) phList.add(bean);
        }
        LineDataSet water=loadData(0xffe51c23,0xffdd191d,waterList,"水分");
        LineDataSet oil=loadData(0xff9c27b0,0xff6aeb9a,oilList,"油脂");
        LineDataSet tprt=loadData(0xff259b24,0xff056f00,tprtList,"温度");
        LineDataSet ph=loadData(0xffff9800,0xffef6c00,phList,"ph");

        LineData lineData=new LineData();
        if (water!=null) lineData.addDataSet(water);
        if (oil!=null) lineData.addDataSet(oil);
        if (tprt!=null) lineData.addDataSet(tprt);
        if (ph!=null)  lineData.addDataSet(ph);
        chart.setData(lineData);
        chart.invalidate();


    }

    private LineDataSet loadData(int color, int circleColor, ArrayList<SkinDataListBean.SkinDataBean> beanList,String label){

        List<Entry> entryList=new ArrayList<>();
        for (SkinDataListBean.SkinDataBean bean:beanList){
            entryList.add(new Entry(entryList.size(),bean.getSkin_value(),bean.getSkin_date()));
        }
        if (entryList.size()>0){
            LineDataSet lineDataSet=new LineDataSet(entryList,label);
            lineDataSet.setColor(color);
            lineDataSet.setCircleColor(circleColor);

            return lineDataSet;
        }
        return null;
    }

    @Override
    public void showSuccess() {
        Toast.makeText(getActivity(),"获取数据成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {

        dialog.setMessage("正在获取数据...");
        dialog.show();
    }

    @Override
    public void showFail() {
        Toast.makeText(getActivity(),"获取失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }
}
