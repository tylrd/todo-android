package com.mtaylord.todo.mvp.presenter.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.mtaylord.todo.mvp.model.Item;
import com.mtaylord.todo.mvp.presenter.ListPresenter;
import com.mtaylord.todo.mvp.view.ItemListView;

import java.util.List;

public class ListPresenterImpl implements ListPresenter, LoaderManager.LoaderCallbacks<List<Item>> {

    private ItemListView itemListView;

    private LoaderManager loaderManager;

    private Loader<List<Item>> loader;

    public ListPresenterImpl(@NonNull ItemListView itemView,
                             @NonNull Loader<List<Item>> loader,
                             @NonNull LoaderManager loaderManager) {
        this.itemListView = itemView;
        this.loader = loader;
        this.loaderManager = loaderManager;
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
    public void startLoadItems() {
        loaderManager.initLoader(1, null, this);
    }

    @Override
    public void onLoadFinished(Loader<List<Item>> loader, List<Item> data) {
        itemListView.showTasks(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Item>> loader) {

    }

    @Override
    public void addNewItem() {

    }

    @Override
    public void deleteItem() {

    }

    @Override
    public void updateItem() {

    }

    @Override
    public void showItem() {

    }

}
