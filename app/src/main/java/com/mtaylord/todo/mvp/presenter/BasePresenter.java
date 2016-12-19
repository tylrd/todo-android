package com.mtaylord.todo.mvp.presenter;

import com.mtaylord.todo.mvp.view.BaseView;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

}
