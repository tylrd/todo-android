package com.mtaylord.todo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mtaylord.todo.ui.fragment.ListFragment;

public class ListPageAdapter extends FragmentStatePagerAdapter {

    public ListPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return "To Do";
            case 1:
                return "Done";
            default:
                return null;
        }
    }
}
