package com.mtaylord.todo.list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mtaylord.todo.list.done.DoneFragment;
import com.mtaylord.todo.list.todo.TodoFragment;

public class ListPageAdapter extends FragmentStatePagerAdapter {

    private final static String[] TABS = {"To Do", "Done"};

    public static final int TODO_PAGE = 0;
    public static final int DONE_PAGE = 1;

    public ListPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TODO_PAGE:
                return TodoFragment.newInstance();
            case DONE_PAGE:
                return DoneFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TABS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < TABS.length) {
            return TABS[position];
        }
        return TABS[0];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
