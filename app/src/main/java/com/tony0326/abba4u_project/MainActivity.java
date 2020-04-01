/*
    Student : 1544021 서준석 - Fragment와 Viewpager 이용하여 전체적인 UI구성
 */
package com.tony0326.abba4u_project;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tony0326.abba4u_project.Module.Adapter.MainPagerAdapter;
import com.tony0326.abba4u_project.Module.ViewPager.CustomViewPager;
import com.tony0326.abba4u_project.R;
import static com.tony0326.abba4u_project.staticData.userID;

public class MainActivity extends AppCompatActivity {
    CustomViewPager viewPager;
    TabLayout tabLayout;
    MainPagerAdapter pa;
    Intent intent;
    UserData user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pa = new MainPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(pa);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                viewPager.setCurrentItem(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }
}
