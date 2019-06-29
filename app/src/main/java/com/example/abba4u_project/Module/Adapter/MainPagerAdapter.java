package com.example.abba4u_project.Module.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.abba4u_project.Module.Fragment.CollageFragment;
import com.example.abba4u_project.Module.Fragment.MandalaFragment;
import com.example.abba4u_project.Module.Fragment.PictureFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static int PAGER_NUMBER = 3;    //페이지 갯수
    private static String PAGE_ONE = "그림테스트";
    private static String PAGE_TWO = "콜라주";
    private static String PAGE_THREE = "만다라";

    public MainPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new PictureFragment();
            case 1:
                return new CollageFragment();
            case 2:
                return new MandalaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGER_NUMBER;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return PAGE_ONE;
            case 1:
                return PAGE_TWO;
            case 2:
                return PAGE_THREE;
                default:
                    return null;
        }
    }
}
