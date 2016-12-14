package com.mtaylord.todo.mvp.presenter;

import com.mtaylord.todo.mvp.model.Item;

import java.util.List;

public interface ListPresenter extends BasePresenter {

    void startLoadItems(int loaderId);

    void addNewItem(String itemName);

    void showAddItem();

    void deleteItem(Item item);

    void removeItem(Item item);

    void addToChecked(Item item);

    void removeFromChecked(Item item);

    void deleteChecked(List<Item> currentItemList);

    void undoRemove();

    void updateItem();

    void showItem();

}
