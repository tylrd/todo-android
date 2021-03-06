package com.mtaylord.todo.itemlist.done;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtaylord.todo.R;
import com.mtaylord.todo.data.ItemListLoader;
import com.mtaylord.todo.data.db.TodoDbHelper;
import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.item.ItemDataSource;
import com.mtaylord.todo.data.source.item.impl.LocalItemDataSource;
import com.mtaylord.todo.itemlist.ItemListAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by taylor on 12/18/16.
 */

public class DoneFragment extends Fragment implements DoneView {

    private DonePresenter mPresenter;
    private ItemListAdapter mAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    public static Fragment newInstance(int listId) {
        Fragment doneFragment = new DoneFragment();
        Bundle args = new Bundle();
        args.putInt("listId", listId);
        doneFragment.setArguments(args);
        return doneFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TodoDbHelper todoDbHelper = TodoDbHelper.getInstance(getActivity().getApplicationContext());
        ItemDataSource itemDataSource = LocalItemDataSource.getInstance(todoDbHelper);
        Loader<List<Item>> loader = new ItemListLoader(getActivity(), itemDataSource, getArguments().getInt("listId"), true);
        mPresenter = new DonePresenterImpl(itemDataSource, getLoaderManager(), loader);
        mPresenter.attachView(this);
        List<Item> emptyList = Collections.emptyList();
        mAdapter = new ItemListAdapter(emptyList);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoadItems();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.itemlist_fragment, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void showRemoveItem(int position) {

    }

    @Override
    public void showInsertItems(List<Item> items) {
        mAdapter.addItem(items);
    }

    @Override
    public void showItemList(List<Item> list) {
        mAdapter.replaceData(list);
    }

    @Override
    public void showUpdatedItemList(List<Item> newList) {

    }

    @Override
    public void showUpdatedItem(Item item, int position) {

    }
}
