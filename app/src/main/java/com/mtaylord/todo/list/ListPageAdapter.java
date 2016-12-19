package com.mtaylord.todo.list;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.mtaylord.todo.data.ItemListLoader;
import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.ItemDataSource;
import com.mtaylord.todo.data.source.impl.ItemDataSourceImpl;
import com.mtaylord.todo.list.done.DoneFragment;
import com.mtaylord.todo.list.done.DonePresenter;
import com.mtaylord.todo.list.done.DonePresenterImpl;
import com.mtaylord.todo.list.done.DoneView;
import com.mtaylord.todo.list.todo.TodoFragment;
import com.mtaylord.todo.list.todo.TodoPresenter;
import com.mtaylord.todo.list.todo.TodoPresenterImpl;
import com.mtaylord.todo.list.todo.TodoView;

import java.util.List;

public class ListPageAdapter extends FragmentStatePagerAdapter {

    private final static String[] TABS = {"To Do", "Done"};

    public static final int TODO_PAGE = 0;
    public static final int DONE_PAGE = 1;

    private final Context context;
    private final LoaderManager loaderManager;

    public ListPageAdapter(FragmentManager fm,
                           Context context,
                           LoaderManager loaderManager) {
        super(fm);
        this.context = context;
        this.loaderManager = loaderManager;
    }

    @Override
    public Fragment getItem(int position) {
        ItemDataSource itemDataSource = ItemDataSourceImpl.getInstance(context);
        Loader<List<Item>> itemListLoader = new ItemListLoader(context, itemDataSource, position != 0);
        Fragment fragment;
        switch (position) {
            case TODO_PAGE:
                fragment = new TodoFragment();
                TodoPresenter todoPresenter = new TodoPresenterImpl(itemDataSource, loaderManager, itemListLoader);
                todoPresenter.attachView((TodoView) fragment);
                return fragment;
            case DONE_PAGE:
                fragment = new DoneFragment();
                DonePresenter donePresenter = new DonePresenterImpl(itemDataSource, loaderManager, itemListLoader);
                donePresenter.attachView((DoneView) fragment);
                return fragment;
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
