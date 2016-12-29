package com.mtaylord.todo.todo.base;

import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.mvp.presenter.Presenter;

import java.util.List;

/**
 * Created by taylor on 12/18/16.
 */

public interface BaseListPresenter<V extends BaseListView> extends Presenter<V> {

    void startLoadItems();

    void addToSelected(Item item);

    void removeFromSelected(Item item);

    void deleteSelected(List<Item> originalList);

    void updateItem(Item item, int position);

}
