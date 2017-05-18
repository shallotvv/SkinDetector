package com.vvxc.skindetector.view.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.MyApplication;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.PhotoPresenter;
import com.vvxc.skindetector.util.GetPicPathUtil;
import com.vvxc.skindetector.view.activity.MainActivity;
import com.vvxc.skindetector.view.activity.PhotoListActivity;
import com.vvxc.skindetector.view.adapter.PhotoRecyclerAdapter;
import com.vvxc.skindetector.view.fragment.view.PhotoFragmentView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by vvxc on 2017/3/24.
 */
public class PhotoFragment extends BaseFragment<PhotoPresenter,PhotoFragmentView> implements View.OnClickListener,PhotoFragmentView {
    private static final String SAVE_PIC_PATH=Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ?Environment.getExternalStorageDirectory().getAbsolutePath():"/mnt/sdcard";//保存到SD卡
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH+ "/com.vvxc.skinDectector/savePic";//保存的确切位置
    private   static final int TAKE_PICTURE=1;
    RecyclerView photoRecyclerView;
    Button cameraBtn,queryYunPic;
    UserInfoBean user;
    View view;
    List<String> pathList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view=inflater.inflate(R.layout.fragment_photo,container,false);
        initView();
        GridLayoutManager gm=new GridLayoutManager(getContext(),3);
        photoRecyclerView.setLayoutManager(gm);
        pathList=GetPicPathUtil.getPicturesInLocal(SAVE_REAL_PATH);
        PhotoRecyclerAdapter adapter= new PhotoRecyclerAdapter(getContext(),pathList);
        photoRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    protected PhotoPresenter createPresenter() {
        return new PhotoPresenter();
    }

    private void initView() {
        photoRecyclerView= (RecyclerView) view.findViewById(R.id.photo_recycler);
        cameraBtn= (Button) view.findViewById(R.id.camera_btn);
        queryYunPic= (Button) view.findViewById(R.id.query_yun_pic);
        queryYunPic.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_btn : openCamera();break;
            case R.id.query_yun_pic :
                MyApplication application= (MyApplication) getActivity().getApplication();
                user=application.getUserInfo();
                if (user==null){
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(getContext(),PhotoListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void openCamera() {
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE) {
            if (resultCode == MainActivity.RESULT_OK) {
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                //  showPhotoImg.setImageBitmap(bm);//想图像显示在ImageView视图上，private ImageView img;
                String fileName = "" + new Date().getTime() + ".jpg";
                presenter.savePhoto(SAVE_REAL_PATH, fileName, bm);
            }
        }
    }

    @Override
    public void refreshRecycler() {
        pathList.clear();
        pathList.addAll(GetPicPathUtil.getPicturesInLocal(SAVE_REAL_PATH));
        photoRecyclerView.getAdapter().notifyDataSetChanged();

    }


}
