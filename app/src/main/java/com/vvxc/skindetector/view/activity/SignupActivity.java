package com.vvxc.skindetector.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vvxc.skindetector.Bean.UserSignupBean;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.SignupPresenter;

public class SignupActivity extends BaseActivity<SignupPresenter,SignupView> implements SignupView,View.OnClickListener{
    private static final String TAG = "SignupActivity";

    EditText nameText;
    EditText emailText;
    EditText mobileText;
    EditText passwordText;
    EditText reEnterPasswordText;
    Button signupButton;
    TextView loginLink;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();
        signupButton.setOnClickListener(this);

        loginLink.setOnClickListener(this);
    }

    @Override
    protected SignupPresenter createPresenter() {
        return new SignupPresenter();
    }

    private void initView() {
        nameText = (EditText) findViewById(R.id.input_name);
        emailText = (EditText) findViewById(R.id.input_email);
        mobileText = (EditText) findViewById(R.id.input_mobile);
        passwordText = (EditText) findViewById(R.id.input_password);
        reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);
        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
    }


    @Override
    public void showSignupFail() {

        Toast.makeText(getBaseContext(), "创建失败", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    @Override
    public void showDialog() {
      //  signupButton.setEnabled(false);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在创建用户...");
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showNameInvalid() {
        nameText.setError("请输入你的昵称");
    }

    @Override
    public void showEmailInvalid() {
        emailText.setError("邮箱地址格式错误");
    }

    @Override
    public void showTeleInvalid() {
        mobileText.setError("手机号码格式错误");
    }

    @Override
    public void showPasswordInvalid() {
        passwordText.setError("密码长度在6到12个字符之间");
    }

    @Override
    public void showReEnterInvalid() {
        reEnterPasswordText.setError("两次输入密码不匹配");
    }

    @Override
    public void goNextContext() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent=new Intent(this,SelectInfoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:
                UserSignupBean user=new UserSignupBean();
                String name,email,tele,password,reEnterPassword;
                name=nameText.getText().toString();
                email=emailText.getText().toString();
                tele=mobileText.getText().toString();
                password=passwordText.getText().toString();
                reEnterPassword=reEnterPasswordText.getText().toString();

                user.setEmail(email);
                user.setName(name);
                user.setTele(tele);
                user.setPassword(password);
                user.setReEnterPassword(reEnterPassword);

                presenter.signup(user);
                break;
            case R.id.link_login:

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }
}