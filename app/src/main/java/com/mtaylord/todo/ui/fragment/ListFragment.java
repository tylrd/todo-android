package com.mtaylord.todo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListFragment extends Fragment implements ItemListView {

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
            Item item = new Item(itemName, null, false, new Date(), new Date());
            mPresenter.addNewItem(item);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Item> tempList = new ArrayList<>(0);
        mAdapter = new ItemAdapter(tempList);
    }

    @Override
    public void onResume() {
        super.onResume();
        int loaderId = getArguments().getInt(LOADER_ID);
        mPresenter.startLoadItems(loaderId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void insertItem(Item item, int position) {
        mAdapter.addItem(item, position);
    }

    @Override
    public void showTasks(List<Item> tasks) {
        mAdapter.replaceData(tasks);
    }

    @Override
    public void setPresenter(ListPresenter presenter) {
        mPresenter = presenter;
    }

}
