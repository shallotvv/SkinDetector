package com.vvxc.skindetector.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vvxc.skindetector.Api.PhotoService;
import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.MyApplication;
import com.vvxc.skindetector.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ShowPhotoActivity extends AppCompatActivity {

    ImageView img;
    Button uploadBtn;

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        img= (ImageView) findViewById(R.id.show_one_photo);
        path=getIntent().getStringExtra("path");
        Glide.with(this).load(path).into(img);

        uploadBtn= (Button) findViewById(R.id.upload_pic_btn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPic();
            }
        });
    }

    private void uploadPic() {
        MyApplication application= (MyApplication) getApplication();
        UserInfoBean user=application.getUserInfo();
        if (null==user){
            Toast.makeText(ShowPhotoActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                //加入解析json格式数据的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PhotoService service= retrofit.create(PhotoService.class);


        File file=new File(path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        String fileName=path.substring(path.lastIndexOf("/"));
        Call<String> call =service.uploadImage("JSESSIONID="+user.getToken(),fileName,requestBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result=response.body();
                if ("1".equals(result)){
                    Toast.makeText(ShowPhotoActivity.this,"图片保存成功",Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("-1".equals(result)){
                    Toast.makeText(ShowPhotoActivity.this,"图片已存在",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ShowPhotoActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}
