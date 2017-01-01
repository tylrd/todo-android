package com.mtaylord.todo.itemlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mtaylord.todo.itemlist.done.DoneFragment;
import com.mtaylord.todo.itemlist.todo.TodoFragment;

public class ListPageAdapter extends FragmentStatePagerAdapter {

    private final static String[] TABS = {"To Do", "Done"};

    public static final int TODO_PAGE = 0;
    public static final int DONE_PAGE = 1;

    private int listId;

    public ListPageAdapter(FragmentManager fm, int listId) {
        super(fm);
        this.listId = listId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TODO_PAGE:
                return TodoFragment.newInstance(listId);
            case DONE_PAGE:
                return DoneFragment.newInstance(listId);
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
