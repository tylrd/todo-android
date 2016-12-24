package com.mtaylord.todo.list.todo;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.ItemDataSource;
import com.mtaylord.todo.list.base.BaseListPresenterImpl;

import java.util.Date;
import java.util.List;

/**
 * Created by taylor on 12/18/16.
 */

public class TodoPresenterImpl extends BaseListPresenterImpl<TodoView> implements TodoPresenter {

    public TodoPresenterImpl(ItemDataSource itemDataSource,
                             LoaderManager loaderManager,
                             Loader<List<Item>> loader) {
        super(itemDataSource, loaderManager, loader);
    }

    @Override
    public Item createItem(String name) {
        Item item = getDataSource().saveItem(name);
        getView().showInsertItem(item);
        return item;
    }

    @Override
    protected int getLoaderId() {
        return 0;
    }
}
