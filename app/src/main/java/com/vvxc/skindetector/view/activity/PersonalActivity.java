package com.vvxc.skindetector.view.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.PersonalPresenter;

public class PersonalActivity extends BaseActivity<PersonalPresenter,PersonalView> implements View.OnClickListener,PersonalView{
    public static final int PERSONAL_TAG=7;
    Button logoutBtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();

    }

    @Override
    protected PersonalPresenter createPresenter() {
        return new PersonalPresenter();
    }

    private void initView() {
        logoutBtn= (Button) findViewById(R.id.btn_logout);
        progressDialog=new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);

        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        switch (v.getId()){
            case R.id.btn_logout:
                presenter.logout(sharedPreferences);break;

        }
    }

    @Override
    public void showProgress() {
        progressDialog.setMessage("正在提交");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void goNextContext() {
        setResult(MainActivity.LOG_OUT);
        finish();
    }

    @Override
    public void showFail() {
        Toast.makeText(this,"提交失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {

    }
}
