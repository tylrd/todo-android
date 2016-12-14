package com.mtaylord.todo.mvp.view;

import com.mtaylord.todo.mvp.model.Item;
import com.mtaylord.todo.mvp.presenter.ListPresenter;

import java.util.List;

public interface ItemListView extends BaseView<ListPresenter> {
    void showItems(List<Item> items);

    void showAddItemDialog();

    void insertItem(Item item, int position);

    void updateItems(List<Item> newItems);

    void subtractItems(List<Item> itemsToRemove);

}
