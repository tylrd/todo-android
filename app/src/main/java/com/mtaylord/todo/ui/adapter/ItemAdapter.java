package com.mtaylord.todo.ui.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mtaylord.todo.R;
import com.mtaylord.todo.mvp.model.Item;
import com.mtaylord.todo.util.ItemListDiffUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnItemCheckedListener {
        void onItemChecked(CompoundButton compoundButton, Item item, int position);

        void onItemUnchecked(CompoundButton compoundButton, Item item, int position);
    }

    private List<Item> mItemList;
    private OnItemClickListener mClickListener;
    private OnItemCheckedListener mItemCheckedListener;

    public ItemAdapter(List<Item> itemList) {
        mItemList = itemList;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    public void setOnItemCheckedListener(OnItemCheckedListener itemCheckedListener) {
        mItemCheckedListener = itemCheckedListener;
    }

    public List<Item> getItemList() {
        return mItemList;
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

    public void replaceData(List<Item> newData) {
        mItemList = newData;
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        mItemList.add(item);
        notifyItemInserted(mItemList.size() - 1);
    }

    public void updateList(List<Item> newData) {
        DiffUtil.DiffResult itemListDiffResult = DiffUtil.calculateDiff(new ItemListDiffUtil(newData, mItemList));
        mItemList.clear();
        mItemList.addAll(newData);
        itemListDiffResult.dispatchUpdatesTo(this);
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
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (mItemCheckedListener != null) {
                        if (checked) {
                            mItemCheckedListener.onItemChecked(
                                    compoundButton,
                                    mItemList.get(getAdapterPosition()),
                                    getAdapterPosition());
                        } else {
                            mItemCheckedListener.onItemUnchecked(
                                    compoundButton,
                                    mItemList.get(getAdapterPosition()),
                                    getAdapterPosition());
                        }
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
            Timber.d("Binding item %s to viewholder", item);
            mItemName.setText(item.getName());
            mCheckbox.setChecked(item.isChecked());
        }
    }

}
