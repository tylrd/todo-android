package com.mtaylord.todo.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mtaylord.todo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by taylor on 12/29/16.
 */

public class TodoListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_todolist);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }
}
