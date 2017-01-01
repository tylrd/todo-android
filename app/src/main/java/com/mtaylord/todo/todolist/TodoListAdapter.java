package com.mtaylord.todo.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mtaylord.todo.R;
import com.mtaylord.todo.data.model.TodoList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by taylor on 12/29/16.
 */

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.Adapter> {

    interface OnListClickListener {
        void onClick(TodoList list);
    }

    private List<TodoList> mList;
    private OnListClickListener mOnListClickListener;

    public TodoListAdapter(List<TodoList> list) {
        mList = list;
    }

    public void setOnListClickListener(OnListClickListener listener) {
        mOnListClickListener = listener;
    }

    @Override
    public Adapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_holder, parent, false);
        return new Adapter(v);
    }

    @Override
    public void onBindViewHolder(Adapter holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    void replaceData(List<TodoList> newList) {
        mList = null;
        mList = newList;
        notifyDataSetChanged();
    }

    class Adapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.list_name) TextView mTextView;

        Adapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnListClickListener != null) {
                mOnListClickListener.onClick(mList.get(getAdapterPosition()));
            }
        }

        void bind(TodoList list) {
            mTextView.setText(list.getName());
        }
    }
}
