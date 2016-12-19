package com.mtaylord.todo.list.base;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.mvp.view.BaseView;

import java.util.List;

/**
 * Created by taylor on 12/18/16.
 */

public interface BaseListView extends BaseView {
    void updateList(List<Item> newList);

    void updateItem(Item item, int position);
}
