package com.mtaylord.todo.todo.todo;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.item.ItemDataSource;
import com.mtaylord.todo.todo.ItemsCompleteEvent;
import com.mtaylord.todo.todo.base.BaseListPresenterImpl;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
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
    public void completeItem(Item item, int position) {
        item.setComplete(true);
        getDataSource().update(item);
        getView().showRemoveItem(position);
        List<Item> items = new ArrayList<>();
        items.add(item);
        EventBus.getDefault().post(new ItemsCompleteEvent(items));
    }

    @Override
    public Item createItem(String name) {
        Item item = new Item(name, false);
        item = getDataSource().create(item);
        getView().showInsertItems(Collections.singletonList(item));
        return item;
    }

    @Override
    protected int getLoaderId() {
        return 0;
    }
}
