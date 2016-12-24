package com.mtaylord.todo.list.done;

import android.support.v4.app.Fragment;

import com.mtaylord.todo.data.model.Item;

import java.util.List;

/**
 * Created by taylor on 12/18/16.
 */

public class DoneFragment extends Fragment implements DoneView {

    public static Fragment newInstance() {
        return new DoneFragment();
    }

    @Override
    public void showInsertItem(Item item) {

    }

    @Override
    public void showItemList(List<Item> list) {

    }

    @Override
    public void showUpdatedItemList(List<Item> newList) {

    }

    @Override
    public void showUpdatedItem(Item item, int position) {

    }
}
