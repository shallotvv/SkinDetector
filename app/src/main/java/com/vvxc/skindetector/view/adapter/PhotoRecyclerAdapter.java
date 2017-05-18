package com.vvxc.skindetector.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.view.activity.ShowPhotoActivity;

import java.util.List;

/**
 * Created by vvxc on 2017/5/18.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<String> pathList;
    //RecyclerView显示的子View
    //该方法返回是ViewHolder，当有可复用View时，就不再调用
    public  PhotoRecyclerAdapter(Context context,List<String> pathList){
        this.context=context;
        this.pathList=pathList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_photo,viewGroup,false);


        return new ViewHolder(v,i);
    }

    //将数据绑定到子View，会自动复用View
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Glide.with(context).load(pathList.get(i)).into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ShowPhotoActivity.class);
                intent.putExtra("path",pathList.get(i));
                context.startActivity(intent);
            }
        });
    }

    //RecyclerView显示数据条数
    @Override
    public int getItemCount() {
        return pathList.size();
    }

    //自定义的ViewHolder,减少findViewById调用次数
    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        //在布局中找到所含有的UI组件
        public ViewHolder(View itemView, int i) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_photo);

        }
    }
}
