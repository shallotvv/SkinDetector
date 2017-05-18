package com.vvxc.skindetector.view.activity;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vvxc.skindetector.Bean.ImgUrlListBean;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.MyApplication;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.model.PhotoModel;
import com.vvxc.skindetector.model.impl.PhotoModelImpl;
import com.vvxc.skindetector.util.GetPicPathUtil;
import com.vvxc.skindetector.view.adapter.PhotoRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhotoListActivity extends AppCompatActivity {
    UserInfoBean user;
    RecyclerView imgListRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        imgListRecycler= (RecyclerView) findViewById(R.id.activity_photo_recycler);

        MyApplication application= (MyApplication) getApplication();

        user=application.getUserInfo();
        new PhotoModelImpl().getPhotoList("getImgList", user.getToken(), new PhotoModel.OnGetPhotoListListener() {
            @Override
            public void onSuccess(ImgUrlListBean urlList) {
                GridLayoutManager gm=new GridLayoutManager(PhotoListActivity.this,3);
                imgListRecycler.setLayoutManager(gm);


                List<String> pathList=new ArrayList<String>();
                for (ImgUrlListBean.Path path:urlList.getUrlList()){
                    pathList.add(Constants.BaseUrl+Constants.PHOTO_URL+"?"+"url="+user.getUser_id()+path.getPath());
                }

                PhotoRecyclerAdapter adapter= new PhotoRecyclerAdapter(PhotoListActivity.this,pathList);
                imgListRecycler.setAdapter(adapter);

            }

            @Override
            public void onFail() {

            }
        });
    }
}
