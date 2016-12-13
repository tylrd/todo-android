package com.mtaylord.todo.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.mtaylord.todo.data.ItemListLoader;
import com.mtaylord.todo.data.source.ItemDataSource;
import com.mtaylord.todo.data.source.impl.ItemDataSourceImpl;
import com.mtaylord.todo.mvp.model.Item;
import com.mtaylord.todo.mvp.presenter.ListPresenter;
import com.mtaylord.todo.mvp.presenter.impl.ListPresenterImpl;
import com.mtaylord.todo.mvp.view.ItemListView;
import com.mtaylord.todo.ui.fragment.ListFragment;

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
        Fragment fragment = ListFragment.newInstance(position);
        initializePresenter(fragment, position);
        return fragment;
    }

    private void initializePresenter(Fragment fragment, int position) {
        ItemListView itemListView = (ItemListView) fragment;
        ItemDataSource itemDataSource = ItemDataSourceImpl.getInstance(context);
        Loader<List<Item>> itemListLoader = new ItemListLoader(context, itemDataSource, position != 0);
        ListPresenter presenter = new ListPresenterImpl(
                itemListView,
                itemListLoader,
                loaderManager,
                itemDataSource);
        presenter.init();
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
}
