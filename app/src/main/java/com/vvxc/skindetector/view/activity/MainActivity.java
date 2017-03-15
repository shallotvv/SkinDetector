package com.vvxc.skindetector.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.vvxc.skindetector.R;
import com.vvxc.skindetector.view.fragment.MainFragment;


public class MainActivity extends ActionBarActivity {
//    TabLayout mTabLayout;
//    ViewPager mViewPager;
//    Toolbar toolbar;
    ImageView imageView;
    FragmentManager fragmentManager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

    }

    private void initView() {
        navigationView= (NavigationView) findViewById(R.id.navigation_view );
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_frame,new MainFragment()).commit();
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_main);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);



        imageView= (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_header_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
//        toolbar= (Toolbar) findViewById(R.id.toolBar);
//        toolbar.setTitle("菜单");
//        setSupportActionBar(toolbar);
//
//        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
//        viewPagerAdapter.addFragment(new TestFragment(), "水分");//添加Fragment
//        viewPagerAdapter.addFragment(new TestFragment(), "油脂");
//        viewPagerAdapter.addFragment(new TestFragment(), "温度");
//        viewPagerAdapter.addFragment(new TestFragment(), "PH");
//        viewPagerAdapter.addFragment(new TestFragment(), "总体评估");
//        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
//
//        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        mTabLayout.addTab(mTabLayout.newTab().setText("水分"));//给TabLayout添加Tab
//        mTabLayout.addTab(mTabLayout.newTab().setText("油脂"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("温度"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("PH"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("总体评估"));
//        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

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

            moveTaskToBack(true);
        }
    }

    public void openDrawer(){

        drawerLayout.openDrawer(navigationView);
    }
}
