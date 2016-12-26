package com.mtaylord.todo.list.todo;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.item.ItemDataSource;
import com.mtaylord.todo.list.ItemCompleteEvent;
import com.mtaylord.todo.list.base.BaseListPresenterImpl;

import org.greenrobot.eventbus.EventBus;

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
        getDataSource().updateItem(item);
        getView().showRemoveItem(position);
        EventBus.getDefault().post(new ItemCompleteEvent(item));
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
