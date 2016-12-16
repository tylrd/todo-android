package com.mtaylord.todo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mtaylord.todo.R;
import com.mtaylord.todo.mvp.model.Item;
import com.mtaylord.todo.mvp.presenter.ListPresenter;
import com.mtaylord.todo.mvp.view.ItemListView;
import com.mtaylord.todo.ui.ItemDialog;
import com.mtaylord.todo.ui.adapter.ItemAdapter;
import com.mtaylord.todo.ui.adapter.ListPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment implements ItemListView, ItemAdapter.OnItemSelectedListener {

    public static final String LOADER_ID = "loaderId";

    public static Fragment newInstance(int loaderId) {
        Fragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(LOADER_ID, loaderId);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private ListPresenter mPresenter;

    private ItemAdapter mAdapter;

    private ItemDialog.DialogListener dialogListener = new ItemDialog.DialogListener() {
        @Override
        public void onDialogPositiveClick(ItemDialog dialog, String itemName) {
            mPresenter.addNewItem(itemName);
        }

        @Override
        public void onDialogNegativeClick(ItemDialog dialog) {
            dialog.dismiss();
        }
    };

    private View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.showAddItem();
        }
    };

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        List<Item> tempList = new ArrayList<>(0);
        mAdapter = new ItemAdapter(tempList);
    }

    @Override
    public void onResume() {
        super.onResume();
        int loaderId = getArguments().getInt(LOADER_ID);
        mPresenter.startLoadItems(loaderId);
    }

    @Override
    public void onItemSelected(Item item) {
        mPresenter.addToSelected(item);
    }

    @Override
    public void onItemUnselected(Item item) {
        mPresenter.removeFromSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);
        mAdapter.setOnItemSelectedListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mPresenter.deleteItem(mAdapter.getItem(position), position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        initializeFloatingActionButton();
        return view;
    }

    private void initializeFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if (getArguments().getInt(LOADER_ID) == ListPageAdapter.TODO_PAGE) {
            fab.setOnClickListener(fabListener);
        }
    }

    @Override
    public void showAddItemDialog() {
        ItemDialog itemDialog = new ItemDialog();
        itemDialog.setDialogListener(dialogListener);
        itemDialog.show(getActivity().getSupportFragmentManager(), "add_item");
    }

    @Override
    public void deleteItem(int position) {
        mAdapter.deleteItem(position);
    }

    @Override
    public void insertItem(Item item) {
        mAdapter.addItem(item);
    }

    @Override
    public void showItems(List<Item> items) {
        mAdapter.replaceData(items);
    }

    @Override
    public void updateItems(List<Item> newItems) {
        mAdapter.updateList(newItems);
    }

    @Override
    public void setPresenter(ListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_delete:
                mPresenter.deleteSelected(mAdapter.getItemList());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
