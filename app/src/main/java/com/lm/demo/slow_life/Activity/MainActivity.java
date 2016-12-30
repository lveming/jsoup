package com.lm.demo.slow_life.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lm.demo.slow_life.R;
import com.lm.demo.slow_life.fragment.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.id_main_top_IB)
    ImageButton mImageButton;
    @BindView(R.id.id_main_top_TV)
    TextView mTopText;
    @BindView(R.id.id_main_TabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.id_main_ViewPager)
    ViewPager mViewPager;

    String[] titles;
    private FragmentPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();

    }

    private void initData() {
        titles=new String[]{"慢随笔","慢动漫","慢音乐","慢电影","慢戏剧","慢阅读"};
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MainFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position%titles.length];
            }
        };
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
