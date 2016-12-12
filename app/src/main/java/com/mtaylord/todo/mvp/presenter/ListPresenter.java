package com.mtaylord.todo.mvp.presenter;

public interface ListPresenter extends BasePresenter {

    void startLoadItems();

    void addNewItem();

    void deleteItem();

    void updateItem();

    void showItem();

}
