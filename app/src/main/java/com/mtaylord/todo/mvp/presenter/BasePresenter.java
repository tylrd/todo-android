package com.mtaylord.todo.mvp.presenter;

import com.mtaylord.todo.mvp.view.BaseView;

public class BasePresenter<V extends BaseView> implements Presenter<V> {

    private V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    public V getView() {
        return mView;
    }

}
