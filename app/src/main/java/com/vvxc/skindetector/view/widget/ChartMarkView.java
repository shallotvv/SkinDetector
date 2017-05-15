package com.vvxc.skindetector.view.widget;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.vvxc.skindetector.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vvxc on 2017/3/27.
 */
public class ChartMarkView extends MarkerView {
    TextView markText;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public ChartMarkView(Context context, int layoutResource) {
        super(context, layoutResource);

        markText= (TextView) findViewById(R.id.mark_text);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        long time= (long) e.getData();
        if (time<9999999999.0){
            time=time*1000;
        }
        Date date=new Date(time);
        Log.i("wxc_date", ""+date.getTime());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        String result=simpleDateFormat.format(date);
        markText.setText(result);
        super.refreshContent(e, highlight);
    }
}
