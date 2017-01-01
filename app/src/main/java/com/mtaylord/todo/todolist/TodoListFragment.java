package com.mtaylord.todo.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtaylord.todo.R;
import com.mtaylord.todo.data.model.TodoList;
import com.mtaylord.todo.itemlist.TodoActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by taylor on 12/29/16.
 */

public class TodoListFragment extends Fragment {

    @BindView(R.id.list_recyclerview) RecyclerView mRecyclerView;

    private TodoListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<TodoList> tempList = Collections.emptyList();
        mAdapter = new TodoListAdapter(tempList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todolist_fragment, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        List<TodoList> dummyData = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            TodoList todoList = new TodoList(String.format(Locale.ENGLISH, "list %d", i));
            todoList.setId(i);
            dummyData.add(todoList);
        }
        mAdapter.replaceData(dummyData);
        mAdapter.setOnListClickListener(new TodoListAdapter.OnListClickListener() {
            @Override
            public void onClick(TodoList list) {
                Intent intent = new Intent(getActivity(), TodoActivity.class);
                intent.putExtra("listId", list.getId());
                startActivity(intent);
            }
        });
        return view;
    }
}
