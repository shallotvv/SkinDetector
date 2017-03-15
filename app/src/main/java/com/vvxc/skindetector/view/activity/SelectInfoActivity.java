package com.vvxc.skindetector.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vvxc.skindetector.Bean.UserSelectInfoBean;
import com.vvxc.skindetector.Constants;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.SelectInfoPresenter;

public class SelectInfoActivity extends BaseActivity<SelectInfoPresenter,SelectInfoView> implements SelectInfoView,View.OnClickListener{

    ImageView boyImg;
    ImageView girlImg;
    ImageView boyCheckImg;
    ImageView girlCheckImg;
    RadioButton denyRd;
    RadioGroup skinTypeGroup;
    Button completeBtn;
    ProgressDialog progressDialog;
  //  CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_info);
        initView();

        girlImg.setOnClickListener(this);
        boyImg.setOnClickListener(this);
        completeBtn.setOnClickListener(this);


    }

    private void initView() {
        boyImg= (ImageView) findViewById(R.id.boy_img);
        girlImg= (ImageView) findViewById(R.id.girl_img);
        boyCheckImg= (ImageView) findViewById(R.id.boy_check);
        girlCheckImg= (ImageView) findViewById(R.id.girl_check);
        denyRd= (RadioButton) findViewById(R.id.radio_deny);
        skinTypeGroup= (RadioGroup) findViewById(R.id.skin_type_group);
        completeBtn= (Button) findViewById(R.id.btn_complete);
        skinTypeGroup.check(R.id.radio_deny);
        progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);

     //   cardView= (CardView) findViewById(R.id.select_info_card);

    }

    @Override
    protected SelectInfoPresenter createPresenter() {
        return new SelectInfoPresenter();
    }

    @Override
    public void showDialog() {

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在提交...");
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void goNextContext() {
        finish();
    }

    @Override
    public void showFail() {
        Toast.makeText(this,"提交失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boy_img:
                if (boyCheckImg.getVisibility()==View.INVISIBLE){
                    boyCheckImg.setVisibility(View.VISIBLE);
                }
                girlCheckImg.setVisibility(View.INVISIBLE);
                break;
            case R.id.girl_img:
                if (girlCheckImg.getVisibility()==View.INVISIBLE){
                   girlCheckImg.setVisibility(View.VISIBLE);
                }
                boyCheckImg.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_complete:
                UserSelectInfoBean user=new UserSelectInfoBean();
                int skinType=0;
                int sex=0;
                int id=skinTypeGroup.getCheckedRadioButtonId();
                if (id==R.id.radio_deny) skinType= Constants.DENY;
                if (id==R.id.radio_normal) skinType= Constants.NOMAL;
                if (id==R.id.oil_radio) skinType= Constants.OIL;
                if (id==R.id.radio_dry) skinType= Constants.DRY;
                if (id==R.id.radio_sensitive) skinType= Constants.SENSTIVE;
                if (id==R.id.radio_combination) skinType= Constants.COMBINATION;
                if (boyCheckImg.getVisibility()==View.VISIBLE) sex=Constants.BOY;
                if (girlCheckImg.getVisibility()==View.VISIBLE) sex=Constants.GIRL;

                user.setSex(sex);
                user.setSkinTyoe(skinType);

                presenter.SelectInfo(user);

                break;
        }
    }
}
