package com.mtaylord.todo.itemlist.base;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.mvp.view.BaseView;

import java.util.List;

/**
 * Created by taylor on 12/18/16.
 */

public interface BaseListView extends BaseView {

    void showInsertItems(List<Item> items);

    void showItemList(List<Item> list);

    void showUpdatedItemList(List<Item> newList);

    void showUpdatedItem(Item item, int position);

    void showRemoveItem(int position);

}
