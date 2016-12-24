package com.mtaylord.todo.list.done;

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
import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.ItemDataSource;
import com.mtaylord.todo.data.source.impl.ItemDataSourceImpl;
import com.mtaylord.todo.list.ItemListAdapter;

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

    public static Fragment newInstance() {
        return new DoneFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemDataSource itemDataSource = ItemDataSourceImpl.getInstance(getActivity());
        Loader<List<Item>> loader = new ItemListLoader(getActivity(), itemDataSource, true);
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
        final View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void showRemoveItem(int position) {

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
    public void showUpdatedItemList(List<Item> newList) {

    }

    @Override
    public void showUpdatedItem(Item item, int position) {

    }
}
