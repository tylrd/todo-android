package com.mtaylord.todo.mvp.presenter.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.SparseArrayCompat;

import com.mtaylord.todo.data.source.ItemDataSource;
import com.mtaylord.todo.mvp.model.Item;
import com.mtaylord.todo.mvp.presenter.ListPresenter;
import com.mtaylord.todo.mvp.view.ItemListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class ListPresenterImpl implements ListPresenter, LoaderManager.LoaderCallbacks<List<Item>> {

    private ItemListView itemListView;
    private LoaderManager loaderManager;
    private Loader<List<Item>> loader;
    private ItemDataSource itemDataSource;
    private List<Item> checkedItems = new ArrayList<>();

    public ListPresenterImpl(@NonNull ItemListView itemView,
                             @NonNull Loader<List<Item>> loader,
                             @NonNull LoaderManager loaderManager,
                             @NonNull ItemDataSource itemDataSource) {
        this.itemListView = itemView;
        this.loader = loader;
        this.loaderManager = loaderManager;
        this.itemDataSource = itemDataSource;
    }

    @Override
    public void init() {
        itemListView.setPresenter(this);
    }

    @Override
    public Loader<List<Item>> onCreateLoader(int id, Bundle args) {
        return loader;
    }

    @Override
    public void startLoadItems(int loaderId) {
        loaderManager.initLoader(loaderId, null, this);
    }

    @Override
    public void onLoadFinished(Loader<List<Item>> loader, List<Item> data) {
        itemListView.showItems(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Item>> loader) {

    }

    @Override
    public void showAddItem() {
        itemListView.showAddItemDialog();
    }

    @Override
    public void addNewItem(String itemName) {
        Item item = new Item(itemName, false);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        itemDataSource.saveItem(item);
        itemListView.insertItem(item, 0);
    }

    @Override
    public void addToChecked(Item item) {
        item.setChecked(true);
        checkedItems.add(item);
        Timber.d("item %d was checked.", item.getId());
    }

    @Override
    public void removeFromChecked(Item item) {
        item.setChecked(false);
        checkedItems.remove(item);
        Timber.d("Item %d removed from checked items", item.getId());
    }

    @Override
    public void deleteChecked() {
        int[] itemIds = new int[checkedItems.size()];
        for (int i = 0; i < itemIds.length; i++) {
            Item item = checkedItems.get(i);
            itemIds[i] = item.getId();
        }
        itemDataSource.deleteItems(itemIds);
        itemListView.subtractItems(checkedItems);
        checkedItems.clear();
    }

    @Override
    public void removeItem(Item item) {

    }

    @Override
    public void undoRemove() {

    }

    @Override
    public void deleteItem(Item item) {

    }

    @Override
    public void updateItem() {

    }

    @Override
    public void showItem() {

    }

}
