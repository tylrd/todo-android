package com.mtaylord.todo.list.done;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.item.ItemDataSource;
import com.mtaylord.todo.list.ItemsCompleteEvent;
import com.mtaylord.todo.list.base.BaseListPresenterImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by taylor on 12/18/16.
 */

public class DonePresenterImpl extends BaseListPresenterImpl<DoneView> implements DonePresenter {

    public DonePresenterImpl(ItemDataSource itemDataSource,
                             LoaderManager loaderManager,
                             Loader<List<Item>> loader) {
        super(itemDataSource, loaderManager, loader);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLoaderId() {
        return 1;
    }

    @Subscribe
    public void onItemCompleted(ItemsCompleteEvent event) {
        List<Item> items = event.getItems();
        getView().showInsertItems(items);
    }

}
