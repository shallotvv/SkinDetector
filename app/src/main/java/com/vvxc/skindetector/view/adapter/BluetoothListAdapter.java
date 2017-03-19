package com.vvxc.skindetector.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vvxc.skindetector.R;

import java.util.List;

/**
 * Created by vvxc on 2017/3/19.
 */
public class BluetoothListAdapter extends BaseAdapter {
    Context context;
    List<BluetoothDevice> data;

    public BluetoothListAdapter (Context context,List<BluetoothDevice> data){
        this.context=context;
        this.data =data;

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        view= LayoutInflater.from(context).inflate(R.layout.item_bluetooth,parent,false);
        TextView name,mac;
        name= (TextView) view.findViewById(R.id.bluetooth_list_name);
        mac= (TextView) view.findViewById(R.id.bluetooth_list_mac);

        name.setText(data.get(position).getName());
        mac.setText(data.get(position).getAddress());


        return view;
    }
}
