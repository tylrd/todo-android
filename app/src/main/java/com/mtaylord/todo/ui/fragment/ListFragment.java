package com.mtaylord.todo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtaylord.todo.R;
import com.mtaylord.todo.model.Item;
import com.mtaylord.todo.model.ItemList;
import com.mtaylord.todo.ui.ItemDialog;
import com.mtaylord.todo.ui.MainActivity;
import com.mtaylord.todo.ui.adapter.TodoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListFragment extends Fragment implements ItemDialog.DialogListener {

    public static Fragment newInstance(int position) {
        Fragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt("type", position);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private TodoAdapter mAdapater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);

        final List<Item> items;
        if (getArguments() != null && getArguments().getInt("type") >= 0) {
            items = ItemList.getList();
            items.add(new Item("Testing", null));
        } else {
            items = ItemList.getList();
            items.add(new Item("Testing", null));
        }

        mAdapater = new TodoAdapter(items);
        mRecyclerView.setAdapter(mAdapater);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((MainActivity) getActivity()).setFabListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemDialog dialog = new ItemDialog();
                dialog.setTargetFragment(ListFragment.this, 0);
                dialog.show(getActivity().getSupportFragmentManager(), "item_dialog");
            }
        });
        return view;
    }

    @Override
    public void onDialogPositiveClick(ItemDialog dialog, String itemName) {

    }

    @Override
    public void onDialogNegativeClick(ItemDialog dialog) {

    }
}
