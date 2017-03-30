package com.vvxc.skindetector.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vvxc.skindetector.Bean.BlogBean;
import com.vvxc.skindetector.R;

import java.util.ArrayList;

/**
 * Created by vvxc on 2017/3/30.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<BlogBean> dataList;

    public RecycleViewAdapter(Context context, ArrayList<BlogBean> dataList){
        this.context=context;
        this.dataList=dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_view,parent,false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.content.setText(dataList.get(position).getContent());
        holder.title.setText(dataList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        Log.i("wxc_recycler_view","size:"+dataList.size());
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;


        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.item_title);
            content= (TextView) itemView.findViewById(R.id.item_content);
        }
    }
}
