package com.mtaylord.todo.data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mtaylord.todo.data.source.item.ItemDataSource;
import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.item.impl.LocalItemDataSource;

import java.util.List;


public class ItemListLoader extends AsyncTaskLoader<List<Item>> {

    private ItemDataSource localDataSource;
    private int listId;
    private boolean complete;

    public ItemListLoader(Context context, ItemDataSource localDataSource, int listId, boolean complete) {
        super(context);
        this.localDataSource = localDataSource;
        this.listId = listId;
        this.complete = complete;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Item> loadInBackground() {
        return localDataSource.getAllByCompletion(listId, complete);
    }

}
