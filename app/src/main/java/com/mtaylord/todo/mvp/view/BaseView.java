package com.mtaylord.todo.mvp.view;

import com.mtaylord.todo.mvp.presenter.BasePresenter;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
