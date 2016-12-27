package com.mtaylord.todo.list.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mtaylord.todo.R;
import com.mtaylord.todo.data.ItemListLoader;
import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.item.ItemDataSource;
import com.mtaylord.todo.data.source.item.impl.LocalItemDataSource;
import com.mtaylord.todo.list.ItemListAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by taylor on 12/18/16.
 */

public class TodoFragment extends Fragment implements TodoView {

    private TodoPresenter mPresenter;
    private ItemListAdapter mAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    public static Fragment newInstance() {
        return new TodoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemDataSource itemDataSource = LocalItemDataSource.getInstance(getActivity());
        Loader<List<Item>> loader = new ItemListLoader(getActivity(), itemDataSource, 0, false);
        mPresenter = new TodoPresenterImpl(itemDataSource, getLoaderManager(), loader);
        mPresenter.attachView(this);
        List<Item> emptyList = Collections.emptyList();
        mAdapter = new ItemListAdapter(emptyList);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoadItems();
    }

    private AddItemDialog.DialogListener dialogListener = new AddItemDialog.DialogListener() {
        @Override
        public void onDialogPositiveClick(AddItemDialog dialog, String itemName) {
            if (itemName != null && !itemName.isEmpty()) {
                mPresenter.createItem(itemName);
            } else {
                Toast.makeText(getActivity(), "Cannot add item without a title", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onDialogNegativeClick(AddItemDialog dialog) {
            dialog.dismiss();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mPresenter.completeItem(mAdapter.getItem(position), position);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemDialog itemDialog = new AddItemDialog();
                itemDialog.setDialogListener(dialogListener);
                itemDialog.show(getActivity().getSupportFragmentManager(), "add_item");
            }
        });

        return view;
    }

    @Override
    public void showRemoveItem(int position) {
        mAdapter.deleteItem(position);
    }

    @Override
    public void showInsertItem(Item item) {
        mAdapter.addItem(item);
    }

    @Override
    public void showItemList(List<Item> list) {
        mAdapter.replaceData(list);
    }

    @Override
    public void showUpdatedItem(Item item, int position) {

    }

    @Override
    public void showUpdatedItemList(List<Item> newList) {

    }
}
