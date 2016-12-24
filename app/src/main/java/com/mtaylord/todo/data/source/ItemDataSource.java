package com.mtaylord.todo.data.source;

import com.mtaylord.todo.data.model.Item;

import java.util.List;

public interface ItemDataSource {

    List<Item> getItems(boolean completed);

    Item saveItem(String name);

    void deleteItems(List<Item> items);

    void deleteItem(Item item);

    void updateItem(Item item);

    void updateItems(List<Item> items);

}
