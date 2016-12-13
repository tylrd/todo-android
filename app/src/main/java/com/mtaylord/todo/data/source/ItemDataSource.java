package com.mtaylord.todo.data.source;

import com.mtaylord.todo.mvp.model.Item;

import java.util.List;

public interface ItemDataSource {

    List<Item> getItems(boolean completed);

    void saveItem(boolean completed);

}
