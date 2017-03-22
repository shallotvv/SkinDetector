package com.vvxc.skindetector.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.MainFrgmPresenter;
import com.vvxc.skindetector.view.fragment.MainFragment;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by vvxc on 2017/3/19.
 */
public class BluetoothListAdapter extends BaseAdapter {
    Context context;
    List<BluetoothDevice> data;
    MainFrgmPresenter presenter;

    public BluetoothListAdapter (Context context,List<BluetoothDevice> data,MainFrgmPresenter presenter){
        this.context=context;
        this.data =data;
        this.presenter=presenter;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        view= LayoutInflater.from(context).inflate(R.layout.item_bluetooth,parent,false);
        TextView name,mac;
        name= (TextView) view.findViewById(R.id.bluetooth_list_name);
        mac= (TextView) view.findViewById(R.id.bluetooth_list_mac);

        name.setText(data.get(position).getName());
        mac.setText(data.get(position).getAddress());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("wxc_bluetooth", "连接蓝牙");
                presenter.connectBluetooth(data.get(position));
            }
        });

        return view;
    }
}
