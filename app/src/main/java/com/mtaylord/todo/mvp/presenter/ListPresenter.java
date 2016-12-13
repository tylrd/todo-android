package com.mtaylord.todo.mvp.presenter;

import com.mtaylord.todo.mvp.model.Item;

public interface ListPresenter extends BasePresenter {

    void startLoadItems(int loaderId);

    void addNewItem(Item item);

    void showAddItem();

    void deleteItem();

    void updateItem();

    void showItem();

}
