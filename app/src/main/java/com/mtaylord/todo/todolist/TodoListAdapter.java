package com.mtaylord.todo.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtaylord.todo.R;
import com.mtaylord.todo.data.model.TodoList;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by taylor on 12/29/16.
 */

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.Adapter> {

    private List<TodoList> mList;

    public TodoListAdapter(List<TodoList> list) {
        mList = list;
    }

    @Override
    public Adapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_holder, parent, false);
        return new Adapter(v);
    }

    @Override
    public void onBindViewHolder(Adapter holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class Adapter extends RecyclerView.ViewHolder {

        Adapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind() {

        }

    }
}
