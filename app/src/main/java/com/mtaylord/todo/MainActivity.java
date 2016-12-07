package com.mtaylord.todo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.mtaylord.todo.model.Item;
import com.mtaylord.todo.model.ItemList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ItemDialog.DialogListener {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;

    private TodoAdapter mAdapter;
    private Menu mMenu;
    private List<Integer> pendingDelete = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setUpRecyclerView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemDialog dialog = new ItemDialog();
                dialog.show(getFragmentManager(), "item_dialog");
            }
        });
    }

    private void setUpRecyclerView() {
        mAdapter = new TodoAdapter(ItemList.getList());

        mAdapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
            }
        });

        mAdapter.setOnItemCheckedListener(new TodoAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(int position) {
                Timber.d("%d checked!", position);
                if (mMenu != null) {
                    mMenu.findItem(R.id.group_delete).setVisible(true);
                }
                pendingDelete.add(position);
            }

            @Override
            public void onItemUnchecked(int position) {
                Timber.d("%d unchecked!", position);
                pendingDelete.remove(Integer.valueOf(position));
                if (mMenu != null && pendingDelete.size() == 0) {
                    mMenu.findItem(R.id.group_delete).setVisible(false);
                }
            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ItemList.getList().remove(position);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onDialogNegativeClick(ItemDialog dialog) {
        dialog.getDialog().dismiss();
    }

    @Override
    public void onDialogPositiveClick(ItemDialog dialog, String itemName) {
        if (itemName != null) {
            Item item = new Item(itemName, null);
            ItemList.getList().add(0, item);
            mAdapter.notifyItemChanged(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        menu.findItem(R.id.group_delete).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.group_delete:
                for (Integer num : pendingDelete) {
                    Timber.d("%d deleted", num);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
