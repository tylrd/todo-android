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
import com.mtaylord.todo.ui.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListFragment extends Fragment implements ItemListView {

    public static Fragment newInstance(int loaderId) {
        Fragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt("loaderId", loaderId);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private ListPresenter mPresenter;

    private FloatingActionButton fab;

    private ItemAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Item> tempList = new ArrayList<>(0);
        mAdapter = new ItemAdapter(tempList);
    }

    @Override
    public void onResume() {
        super.onResume();
        int loaderId = getArguments().getInt("loaderId");
        mPresenter.startLoadItems(loaderId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        return view;
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
