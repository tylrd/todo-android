package com.mtaylord.todo.mvp.view;

import com.mtaylord.todo.mvp.model.Item;
import com.mtaylord.todo.mvp.presenter.ListPresenter;

import java.util.List;

public interface ItemListView extends BaseView<ListPresenter> {
    void showTasks(List<Item> tasks);
}
