package com.mtaylord.todo.data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mtaylord.todo.mvp.model.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemListLoader extends AsyncTaskLoader<List<Item>> {

    public ItemListLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Item> loadInBackground() {
        List<Item> list = new ArrayList<>();
        list.add(new Item("testing", null));
        return list;
    }

}
