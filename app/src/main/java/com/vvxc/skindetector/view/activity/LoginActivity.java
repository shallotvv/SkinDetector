package com.vvxc.skindetector.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Bean.UserLoginBean;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter,LoginView> implements View.OnClickListener,LoginView {
    private static final String TAG = "LoginActivity";
    private static final int SIGN_UP_OK = 4;
    private static final int REQUEST_SIGNUP = 0;

    EditText inputTele;
    EditText passwordText;
    Button loginButton;
    TextView signupLink;
    ProgressDialog progressDialog;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();
        loginButton.setOnClickListener(this);

        signupLink.setOnClickListener(this);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    private void initView() {

        inputTele = (EditText) findViewById(R.id.input_tele);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                setResult(SIGN_UP_OK);
                // By default we just finish the Activity and log them in automatically
            }
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
       // moveTaskToBack(true);
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                UserLoginBean user=new UserLoginBean();
                String tele = inputTele.getText().toString();
                String password = passwordText.getText().toString();

                user.setPhone(tele);
                user.setPassword(password);
                presenter.login(user);break;

            case R.id.link_signup:
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                //判断用户是否注册成功，如果成功，返回ok，LoginActivity  finnish。
                startActivityForResult(intent, REQUEST_SIGNUP);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            default:;
        }
    }

    @Override
    public void showLoginDialog() {
     //   loginButton.setEnabled(false);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在登陆...");
        progressDialog.show();
    }

    @Override
    public void hideLoginDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void goNextContext(UserInfoBean user) {
        loginButton.setEnabled(true);
        Intent intent=new Intent();
        intent.putExtra("user",user);
        setResult(MainActivity.LOGIN_SUCCESS,intent);
        finish();

    }

    @Override
    public void showLoginFail() {

        Toast.makeText(getBaseContext(), "账号或密码错误", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    @Override
    public void showTeleInvaid() {
        inputTele.setError("手机号码格式错误");
    }

    @Override
    public void showPassWordInvaid() {
        passwordText.setError("密码长度不符合");
    }
}
