package com.mtaylord.todo.util;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.mtaylord.todo.mvp.model.Item;

import java.util.List;

/**
 * Created by taylor on 12/13/16.
 */

public class ItemListDeleteDiffUtil extends DiffUtil.Callback {

    private List<Item> oldList;
    private List<Item> newList;

    public ItemListDeleteDiffUtil(List<Item> newList, List<Item> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() != newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return !oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
