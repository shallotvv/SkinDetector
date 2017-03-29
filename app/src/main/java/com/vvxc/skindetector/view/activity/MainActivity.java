package com.vvxc.skindetector.view.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.presenter.MainPresenter;
import com.vvxc.skindetector.view.fragment.DataFragment;
import com.vvxc.skindetector.view.fragment.MainFragment;
import com.vvxc.skindetector.view.fragment.RankFragment;
import com.vvxc.skindetector.view.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<MainPresenter,MainView> implements MainView{
    public final static  int MAIN_TAG=12344;
    public final static  int LOGIN_SUCCESS=1;
    private static final int SIGN_UP_OK = 4;
    public final static  int LOGIN_FAIL=0;
    public final static int LOG_OUT=5;
    private static boolean isLogin=false;

//    TabLayout mTabLayout;
//    ViewPager mViewPager;
//    Toolbar toolbar;
    ImageView imageView;
    FragmentManager fragmentManager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Fragment mainFragment,dataFragment,photoFragment,communicatFragment,rankFragment;
    List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

        /**
         * 当用户打开应用时，先尝试读取本地的SharedPreferences
         * 如果用户登录过，那本地会保存有之前的登录信息
         * 完成自动登录
         */
        presenter.loginByToken(getSharedPreferences("user",MODE_PRIVATE));

        Resources resources=getResources();
        Resources resource=(Resources)getBaseContext().getResources();
        ColorStateList csl=(ColorStateList)resource.getColorStateList(R.color.navigation_menu_item_color);
        navigationView.setBackgroundColor(0xffffffff);
        navigationView.setItemTextColor(csl);  //设置list text的颜色
        navigationView.setItemIconTintList(null);  //解决不显示icon原来的颜色
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main:
                        hideFragment();
                        fragmentManager.beginTransaction().show(mainFragment).commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case R.id.data:hideFragment();
                        fragmentManager.beginTransaction().show(dataFragment).commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case R.id.photo:hideFragment();
                        fragmentManager.beginTransaction().show(photoFragment).commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case R.id.communication:hideFragment();
                        fragmentManager.beginTransaction().show(communicatFragment).commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case R.id.rank:
                        hideFragment();
                        fragmentManager.beginTransaction().show(rankFragment).commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    private void initView() {
        navigationView= (NavigationView) findViewById(R.id.navigation_view );
        fragmentManager=getSupportFragmentManager();
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_main);
        imageView= (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_header_img);
        mainFragment=new MainFragment();
        rankFragment=new RankFragment();
        dataFragment=new DataFragment();
        photoFragment=new TestFragment();
        communicatFragment=new TestFragment();
        fragmentList=new ArrayList<>();

        fragmentList.add(rankFragment);
        fragmentList.add(mainFragment);
        fragmentList.add(dataFragment);
        fragmentList.add(photoFragment);
        fragmentList.add(communicatFragment);

        FragmentTransaction transaction;
        transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.main_frame,mainFragment,"main");
        transaction.add(R.id.main_frame,rankFragment,"rank");
        transaction.add(R.id.main_frame,dataFragment,"data");
        transaction.add(R.id.main_frame,communicatFragment,"communicat");
        transaction.add(R.id.main_frame,photoFragment,"photo");
        transaction.commit();
        hideFragment();

        transaction=fragmentManager.beginTransaction();
        transaction.show(mainFragment);
        transaction.commit();


        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin){
                    Intent intent=new Intent(MainActivity.this,PersonalActivity.class);
                    startActivityForResult(intent,PersonalActivity.PERSONAL_TAG);
                    return;
                }
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent,MAIN_TAG);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==MAIN_TAG){
            Log.i("wxc_login,result:",resultCode+"");
            if (resultCode==LOGIN_SUCCESS)
            {
                UserInfoBean user= (UserInfoBean) data.getSerializableExtra("user");
                setName(user.getUser_name());
                //登录成功后改变用户名字和头像，并更改用户状态为已登录，点击头像应该跳转到个人主页而不是登录界面了
                setLogin(true);
                return;
            }
            if (resultCode==SIGN_UP_OK)
            {
                presenter.loginByToken(getSharedPreferences("user",MODE_PRIVATE));
                return;
            }
        }

        if (requestCode==PersonalActivity.PERSONAL_TAG){
            if (resultCode==LOG_OUT){
                isLogin=false;
                setName("未登录");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void hideFragment() {
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        for (Fragment fragment :
                fragmentList) {
            transaction.hide(fragment);
        }
        transaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)){
            drawerLayout.closeDrawer(navigationView);
        }else{

            super.onBackPressed();
        }
    }

    public void openDrawer(){

        drawerLayout.openDrawer(navigationView);
    }

    public void setName(String name ){
        TextView nameText= (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
        nameText.setText(name);
    }

    @Override
    public void setLogin(boolean isLogin) {
        this.isLogin=isLogin;
    }

    public boolean getLoginState(){
        return isLogin;
    }

}
