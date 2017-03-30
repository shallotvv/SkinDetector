package com.vvxc.skindetector.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vvxc.skindetector.Bean.BlogBean;
import com.vvxc.skindetector.R;
import com.vvxc.skindetector.view.activity.MainActivity;
import com.vvxc.skindetector.view.adapter.RecycleViewAdapter;
import com.vvxc.skindetector.view.widget.RecycleViewDivider;

import java.util.ArrayList;

/**
 * Created by vvxc on 2017/3/30.
 */
public class CommunicatFragment extends Fragment {
    Handler handler;
    View view;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefleshLayout;
    Toolbar toolbar;

    ArrayList<BlogBean> dataList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.fragment_communicat,container,false);
        initData();
        initView();
        return  view;
    }

    private void initData() {
        dataList=new ArrayList<>();
        BlogBean blogBean1=new BlogBean();
        blogBean1.setTitle("求推荐一款平价洗面奶");
        blogBean1.setContent("如题，油性皮肤用的");
        BlogBean blogBean2=new BlogBean();
        blogBean2.setTitle("最近经常长痘痘...");
        blogBean2.setContent("亲们有什么祛痘心得吗，来分享一下");
        BlogBean blogBean3=new BlogBean();
        blogBean3.setTitle("跟大家分享一些护肤品");
        blogBean3.setContent("1楼祭天，2楼开始说");
        BlogBean blogBean4=new BlogBean();
        blogBean4.setTitle("最近脸上水分得分有点低...");
        blogBean4.setContent("RT，有人给推荐一些保湿的吗");
        BlogBean blogBean5=new BlogBean();
        blogBean5.setTitle("科颜氏高保湿霜适合92年的么？");
        blogBean5.setContent("一个朋友建议我不买。我皮肤没有什么大问题，就是...");
        BlogBean blogBean6=new BlogBean();
        blogBean6.setTitle("使用过黛珂的仙女来说说感受呀");
        blogBean6.setContent("想入这款 混合肌 有红血丝 不知道适不适合呀？");
        BlogBean blogBean7=new BlogBean();
        blogBean7.setTitle("睡不着，30岁以上的来讨论如何抗衰老抗氧化");
        blogBean7.setContent("90后，学生党，都在自称阿姨,作为80后，70后，能不能出来冒个泡,谈谈自己的护肤产品，我们一起来种草拔草");
        BlogBean blogBean8=new BlogBean();
        blogBean8.setTitle("一只干皮的自我拯救");
        blogBean8.setContent("貌美如花的红腰子镇楼");
        BlogBean blogBean9=new BlogBean();
        blogBean9.setTitle("敏感肌适用护肤品+护肤心得");
        blogBean9.setContent("高中皮肤开始出油，所以开始用洗面奶，那时候用的是妮维雅控油。那时候一天用洗");
        BlogBean blogBean10=new BlogBean();
        blogBean10.setTitle("蛋蛋后混油痘皮一枚，坐标美帝");
        blogBean10.setContent("护肤3年彩妆1年。主要祛痘补水美白主欧美日年幼无知买了韩国的毁了皮就再也不敢啦 成分党（ahh sort of）。开个帖子想跟大家分享自己的一点点经验。欢迎各位大神来这里交流");

        dataList.add(blogBean1);
        dataList.add(blogBean2);
        dataList.add(blogBean3);
        dataList.add(blogBean4);
        dataList.add(blogBean5);
        dataList.add(blogBean6);
        dataList.add(blogBean7);
        dataList.add(blogBean8);
        dataList.add(blogBean9);
        dataList.add(blogBean10);
    }

    private void initView() {
        toolbar= (Toolbar) view.findViewById(R.id.communicat_toolBar);
        toolbar.setTitle("社区");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (MainActivity)getActivity()).openDrawer();
            }
        });

        recyclerView= (RecyclerView) view.findViewById(R.id.communicat_list);
        swipeRefleshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        handler=new Handler();

        swipeRefleshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefleshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity() );
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecycleViewAdapter(getActivity(),dataList));
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL,10,0xffffffff));


    }
}
