package com.mtaylord.todo.todo.todo;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.todo.base.BaseListPresenter;

/**
 * Created by taylor on 12/18/16.
 */

public interface TodoPresenter extends BaseListPresenter<TodoView> {

    Item createItem(String name);

    void completeItem(Item item, int position);

}
