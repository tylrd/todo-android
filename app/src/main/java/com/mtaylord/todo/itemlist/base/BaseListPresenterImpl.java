package com.mtaylord.todo.itemlist.base;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.item.ItemDataSource;
import com.mtaylord.todo.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

/**
 * Created by taylor on 12/18/16.
 */

public abstract class BaseListPresenterImpl<V extends BaseListView> extends BasePresenter<V> implements BaseListPresenter<V>,
        LoaderManager.LoaderCallbacks<List<Item>> {

    private LoaderManager loaderManager;
    private Loader<List<Item>> loader;
    private ItemDataSource itemDataSource;
    private List<Item> selectedItems = new ArrayList<>();

    public BaseListPresenterImpl(ItemDataSource itemDataSource,
                                 LoaderManager loaderManager,
                                 Loader<List<Item>> loader) {
        this.itemDataSource = itemDataSource;
        this.loaderManager = loaderManager;
        this.loader = loader;
    }

    @Override
    public void startLoadItems() {
        loaderManager.initLoader(getLoaderId(), null, this);
    }

    @Override
    public void onLoadFinished(Loader<List<Item>> loader, List<Item> data) {
        getView().showItemList(data);
    }

    @Override
    public Loader<List<Item>> onCreateLoader(int id, Bundle args) {
        return loader;
    }

    @Override
    public void onLoaderReset(Loader<List<Item>> loader) {

    }

    @Override
    public void updateItem(Item item, int position) {
        item.setComplete(true);
        item.setUpdated(new Date());
        itemDataSource.update(item);
        getView().showUpdatedItem(item, position);
    }

    @Override
    public void addToSelected(Item item) {
        item.setSelected(true);
        selectedItems.add(item);
    }

    @Override
    public void removeFromSelected(Item item) {
        item.setSelected(false);
        selectedItems.remove(item);
    }

    @Override
    public void deleteSelected(List<Item> originalList) {
        if (!selectedItems.isEmpty()) {
            List<Item> newList = removeSelectedItems(originalList);
            itemDataSource.deleteAll(selectedItems);
            getView().showUpdatedItemList(newList);
            selectedItems.clear();
        } else {
            Timber.d("No checked items to delete.");
        }
    }

    private List<Item> removeSelectedItems(List<Item> originalList) {
        List<Item> newList = new ArrayList<>();
        for (Item originalItem : originalList) {
            if (!selectedItems.contains(originalItem)) {
                newList.add(originalItem);
            }
        }
        return newList;
    }

    public ItemDataSource getDataSource() {
        return itemDataSource;
    }

    protected abstract int getLoaderId();
}
