package com.mtaylord.todo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mtaylord.todo.R;
import com.mtaylord.todo.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnItemCheckedListener {
        void onItemChecked(int position);
        void onItemUnchecked(int position);
    }

    private List<Item> mItemList;
    private OnItemClickListener mClickListener;
    private OnItemCheckedListener mItemCheckedListener;

    public TodoAdapter(List<Item> itemList) {
        mItemList = itemList;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    public void setOnItemCheckedListener(OnItemCheckedListener itemCheckedListener) {
        mItemCheckedListener = itemCheckedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_holder, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name) TextView mItemName;
        @BindView(R.id.item_checked) CheckBox mCheckbox;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setItemClickListener();
            setCheckBoxListener();
        }

        private void setCheckBoxListener() {
            mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (mItemCheckedListener != null) {
                        if (b) mItemCheckedListener.onItemChecked(getAdapterPosition());
                        else mItemCheckedListener.onItemUnchecked(getAdapterPosition());
                    }
                }
            });

        }

        private void setItemClickListener() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
        }

        public void bind(Item item) {
            mItemName.setText(item.getName());
        }
    }

}
