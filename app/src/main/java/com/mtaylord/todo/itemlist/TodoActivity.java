package com.mtaylord.todo.itemlist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mtaylord.todo.R;
import com.mtaylord.todo.util.LockableViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.itemlist_fab) FloatingActionButton mFab;
    @BindView(R.id.pager) LockableViewPager mPager;
    @BindView(R.id.sliding_tabs) TabLayout mTabLayout;

    private ListPageAdapter mPageAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mPageAdapter = new ListPageAdapter(getSupportFragmentManager(), getIntent().getIntExtra("listId", -1));
        mPager.setAdapter(mPageAdapter);
        mPager.setSwipeable(false);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mFab != null) {
                    if (position == 0 && !mFab.isShown()) {
                        mFab.show();
                    } else {
                        mFab.hide();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
