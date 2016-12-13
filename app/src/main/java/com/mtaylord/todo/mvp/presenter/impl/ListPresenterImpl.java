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

import java.util.List;
import java.util.Stack;

import timber.log.Timber;

public class ListPresenterImpl implements ListPresenter, LoaderManager.LoaderCallbacks<List<Item>> {

    private ItemListView itemListView;
    private LoaderManager loaderManager;
    private Loader<List<Item>> loader;
    private ItemDataSource itemDataSource;
    private Stack<Item> undoStack = new Stack<>();
    private SparseArrayCompat<Item> checkedList = new SparseArrayCompat<>();

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
        itemListView.showTasks(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Item>> loader) {

    }

    @Override
    public void showAddItem() {
        itemListView.showAddItemDialog();
    }

    @Override
    public void addNewItem(Item item) {
        itemDataSource.saveItem(item);
        itemListView.insertItem(item, 0);
    }

    @Override
    public void addToChecked(Item item, int position) {
        checkedList.append(position, item);
        Timber.i("item %d checked and added to %d position in checkedItems array", item.getId(), position);
    }

    @Override
    public void removeFromChecked(int position) {
        checkedList.delete(position);
        Timber.i("%d position removed from checked items", position);
    }

    @Override
    public void deleteChecked() {
        for (int i = 0; i < checkedList.size(); i++) {
            int key = checkedList.keyAt(i);
            Item item = checkedList.get(key);
            Timber.d("Item %d has been removed", item.getId());
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
