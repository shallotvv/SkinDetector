package com.vvxc.skindetector.view.fragment;

import android.os.Bundle;

import java.util.Date;

/**
 * Created by vvxc on 2017/3/25.
 * 使用简单工厂模式创建四个fragment，分别具有不同的坐标上限、label和满分标准。
 */
public class AnnalysisFrgmFactory {

    public static final  int WATER_FRGM=1;
    public static final  int OIL_FRGM=2;
    public static final  int TEMPORATRY_FRGM=3;
    public static final  int PH_FRGM=4;

    public AnnalysisFragment createAnnalysisFrgm(int type){
        switch (type){
            case WATER_FRGM:return createWaterFrgm();
            case OIL_FRGM:return createOilFrgm();
            case TEMPORATRY_FRGM:return createTprtFrgm();
            case PH_FRGM:return createPhFrgm();
            default:return null;
        }

    }

    private AnnalysisFragment createPhFrgm() {
        AnnalysisFragment fragment=new AnnalysisFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("y",12);
        bundle.putInt("x",20);
        bundle.putInt("type",PH_FRGM);
        fragment.setArguments(bundle);
        fragment.setFullMarks(6);
        fragment.setUnit("");
        fragment.setLabel("ph");
        return fragment;
    }

    private AnnalysisFragment createTprtFrgm() {
        AnnalysisFragment fragment=new AnnalysisFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("y",100);
        bundle.putInt("x",20);
        bundle.putInt("type",TEMPORATRY_FRGM);
        fragment.setArguments(bundle);
        fragment.setFullMarks(36);
        fragment.setUnit("°");
        fragment.setLabel("温度");
        return fragment;
    }

    private AnnalysisFragment createOilFrgm() {
        AnnalysisFragment fragment=new AnnalysisFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("y",100);
        bundle.putInt("x",20);
        bundle.putInt("type",OIL_FRGM);
        fragment.setArguments(bundle);
        fragment.setFullMarks(25);
        fragment.setUnit("%");
        fragment.setLabel("油脂");
        return fragment;
    }

    private AnnalysisFragment createWaterFrgm() {
        AnnalysisFragment fragment=new AnnalysisFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("y",100);
        bundle.putInt("x",20);
        bundle.putInt("type",WATER_FRGM);
        fragment.setArguments(bundle);

        fragment.setUnit("%");
        fragment.setFullMarks(25);
        fragment.setLabel("水分");
        return fragment;
    }

}
