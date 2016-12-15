package com.mtaylord.todo.mvp.presenter.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

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
    private List<Item> selectedItems = new ArrayList<>();

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
        itemListView.insertItem(item);
    }

    @Override
    public void addToSelected(Item item) {
        item.setChecked(true);
        selectedItems.add(item);
        Timber.d("Item checked: %s", item);
    }

    @Override
    public void removeFromSelected(Item item) {
        item.setChecked(false);
        selectedItems.remove(item);
        Timber.d("Item unchecked: %s", item);
    }

    @Override
    public void deleteSelected(List<Item> originalList) {
        if (!selectedItems.isEmpty()) {
            List<Item> newList = new ArrayList<>();
            for (Item originalItem : originalList) {
                if (!selectedItems.contains(originalItem)) {
                    newList.add(originalItem);
                }
            }
            itemDataSource.deleteItems(selectedItems);
            itemListView.updateItems(newList);
            selectedItems.clear();
        } else {
            Timber.d("No checked items to delete.");
        }
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
