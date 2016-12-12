package com.mtaylord.todo.mvp.presenter;

public interface ListPresenter extends BasePresenter {

    void startLoadItems(int loaderId);

    void addNewItem();

    void deleteItem();

    void updateItem();

    void showItem();

}
