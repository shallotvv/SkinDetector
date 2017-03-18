package com.vvxc.skindetector.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.vvxc.skindetector.R;
import com.vvxc.skindetector.view.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main:
                        hideFragment();
                        drawerLayout.closeDrawer(navigationView);
                        fragmentManager.beginTransaction().show(mainFragment).commit();
                        break;
                    case R.id.data:break;
                    case R.id.photo:break;
                    case R.id.communication:break;
                    case R.id.rank:break;
                }
                return true;
            }
        });

    }


    private void initView() {
        navigationView= (NavigationView) findViewById(R.id.navigation_view );
        fragmentManager=getSupportFragmentManager();
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_main);
        imageView= (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_header_img);
        mainFragment=new MainFragment();
        fragmentList=new ArrayList<Fragment>();

        fragmentList.add(mainFragment);

        fragmentManager.beginTransaction().replace(R.id.main_frame,mainFragment).commit();
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    private void hideFragment() {
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        for (Fragment fragment :
                fragmentList) {
            transaction.hide(fragment).commit();

        }
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
