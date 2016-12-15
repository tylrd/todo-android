package com.mtaylord.todo.ui.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
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

    public interface OnItemSelectedListener {
        void onItemSelected(Item item);
        void onItemUnselected(Item item);
    }

    private List<Item> mItemList;
    private OnItemSelectedListener mSelectedListener;
    private SparseBooleanArray selectedItems;

    public ItemAdapter(List<Item> itemList) {
        mItemList = itemList;
        selectedItems = new SparseBooleanArray();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener selectedClickListener) {
        mSelectedListener = selectedClickListener;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_name) TextView mItemName;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            boolean valueFound = selectedItems.get(getAdapterPosition(), false);
            if (valueFound) {
                selectedItems.delete(getAdapterPosition());
                Timber.d("Selected rows: %s", selectedItems);
                view.setSelected(false);
                if (mSelectedListener != null) {
                    mSelectedListener.onItemUnselected(mItemList.get(getAdapterPosition()));
                }
            } else {
                selectedItems.put(getAdapterPosition(), true);
                Timber.d("Selected rows: %s", selectedItems);
                view.setSelected(true);
                if (mSelectedListener != null) {
                    mSelectedListener.onItemSelected(mItemList.get(getAdapterPosition()));
                }
            }
        }

        public void bind(Item item) {
            Timber.d("Binding item %s to viewholder", item);
            mItemName.setText(item.getName());
        }
    }

}
