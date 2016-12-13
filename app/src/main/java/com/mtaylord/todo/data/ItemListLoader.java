package com.mtaylord.todo.data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mtaylord.todo.data.source.ItemDataSource;
import com.mtaylord.todo.mvp.model.Item;

import java.util.List;


public class ItemListLoader extends AsyncTaskLoader<List<Item>> {

    private ItemDataSource itemDataSource;
    private boolean complete;

    public ItemListLoader(Context context, ItemDataSource dataSource, boolean complete) {
        super(context);
        this.itemDataSource = dataSource;
        this.complete = complete;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Item> loadInBackground() {
        return itemDataSource.getItems(complete);
    }

}
