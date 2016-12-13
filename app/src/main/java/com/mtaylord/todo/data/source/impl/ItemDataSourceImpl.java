package com.mtaylord.todo.data.source.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mtaylord.todo.data.db.TodoContract;
import com.mtaylord.todo.data.db.TodoDbHelper;
import com.mtaylord.todo.data.source.ItemDataSource;
import com.mtaylord.todo.mvp.model.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;

public class ItemDataSourceImpl implements ItemDataSource {

    private static ItemDataSource INSTANCE;

    private TodoDbHelper mDbHelper;

    private SQLiteDatabase mDb;

    public static ItemDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ItemDataSourceImpl(context);
        }
        return INSTANCE;
    }

    private ItemDataSourceImpl(Context context) {
        mDbHelper = new TodoDbHelper(context, 1);
        mDb = mDbHelper.getWritableDatabase();
    }

    @Override
    public List<Item> getItems(boolean isComplete) {
        List<Item> items = new ArrayList<>();
        try {
            String[] projection = {
                    TodoContract.ItemEntry.COLUMN_NAME,
                    TodoContract.ItemEntry.COLUMN_COMPLETE,
                    TodoContract.ItemEntry.COLUMN_CREATED,
                    TodoContract.ItemEntry.COLUMN_UPDATED
            };

            Cursor cursor = mDb.query(TodoContract.ItemEntry.TABLE_NAME, projection, null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_NAME));
                    int completed = cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_COMPLETE));
                    boolean itemCompleted = completed != 0;
                    if (itemCompleted == isComplete) {
                        Item item = new Item(name, null, itemCompleted, null, null);
                        items.add(item);
                    }
                }
            }

//            Collections.sort(items, new Comparator<Item>() {
//                @Override
//                public int compare(Item item, Item t1) {
//                    return item.getCreated().compareTo(t1.getCreated());
//                }
//            });

            if (cursor != null) {
                cursor.close();
            }

        } catch (IllegalStateException e) {
            Timber.e(e);
        }
        return items;
    }

    @Override
    public void saveItem(Item item) {
        try {
            if (item == null) {
                return;
            }

            DateFormat df = SimpleDateFormat.getDateTimeInstance();
            String time = df.format(item.getCreated());

            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoContract.ItemEntry.COLUMN_NAME, item.getName());
            contentValues.put(TodoContract.ItemEntry.COLUMN_COMPLETE, item.isComplete() ? 1 : 0);
            contentValues.put(TodoContract.ItemEntry.COLUMN_CREATED, time);
            contentValues.put(TodoContract.ItemEntry.COLUMN_UPDATED, time);
            mDb.insert(TodoContract.ItemEntry.TABLE_NAME, null, contentValues);

        } catch (IllegalStateException e) {
            Timber.e(e, "Database Error");
        }

    }

}
