package com.mtaylord.todo.mvp.presenter;

import com.mtaylord.todo.mvp.model.Item;

public interface ListPresenter extends BasePresenter {

    void startLoadItems(int loaderId);

    void addNewItem(String itemName);

    void showAddItem();

    void deleteItem(Item item);

    void removeItem(Item item);

    void addToChecked(Item item, int position);

    void removeFromChecked(int position);

    void deleteChecked();

    void undoRemove();

    void updateItem();

    void showItem();

}
