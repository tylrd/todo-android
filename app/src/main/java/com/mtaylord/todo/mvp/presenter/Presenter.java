package com.mtaylord.todo.mvp.presenter;

import com.mtaylord.todo.mvp.view.BaseView;

/**
 * Created by taylordaugherty on 12/23/16.
 */

public interface Presenter<V extends BaseView> {

    void attachView(V view);

}
