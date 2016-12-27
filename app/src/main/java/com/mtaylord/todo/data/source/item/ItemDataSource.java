package com.mtaylord.todo.data.source.item;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.DataSource;

import java.util.List;

public interface ItemDataSource extends DataSource<Item> {

    List<Item> getAllByCompletion(int listId, boolean complete);

    List<Item> getAllByList(int listId);

    List<Item> getAllContaining(String search);

}
