package com.mtaylord.todo.data.source.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mtaylord.todo.BuildConfig;
import com.mtaylord.todo.data.db.TodoContract;
import com.mtaylord.todo.data.db.TodoDbHelper;
import com.mtaylord.todo.data.source.ItemDataSource;
import com.mtaylord.todo.mvp.model.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                    TodoContract.ItemEntry._ID,
                    TodoContract.ItemEntry.COLUMN_NAME,
                    TodoContract.ItemEntry.COLUMN_COMPLETE,
                    TodoContract.ItemEntry.COLUMN_CREATED,
                    TodoContract.ItemEntry.COLUMN_UPDATED
            };

            String selection = String.format("%s = ?", TodoContract.ItemEntry.COLUMN_COMPLETE);
            String[] selectionArgs = isComplete ? new String[]{"1"} : new String[]{"0"};

            Cursor cursor = mDb.query(TodoContract.ItemEntry.TABLE_NAME,
                    projection, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry._ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_NAME));
                    int completed = cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_COMPLETE));
                    String created = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_CREATED));
                    Timber.d("Item %d loaded with name: %s, completed: %b, created_ts: %s", id, name, completed != 0, created);
                    Item item = new Item(name, isComplete);
                    item.setId(id);
                    items.add(item);
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
            long id = mDb.insert(TodoContract.ItemEntry.TABLE_NAME, null, contentValues);
            Timber.d("Item successfully saved: %s", item);
            item.setId((int) id);

        } catch (IllegalStateException e) {
            Timber.e(e, "Error saving item %s", item.getName());
        }

    }

    @Override
    public void deleteItems(List<Item> items) {
        try {
            if (items == null || items.isEmpty()) {
                return;
            }

            StringBuilder builder = new StringBuilder();
            String[] args = new String[items.size()];

            for (int i = 0; i < args.length; i++) {
                builder.append("?");
                if (i != args.length - 1) {
                    builder.append(",");
                }
                args[i] = Integer.toString(items.get(i).getId());
            }

            String ids = builder.toString();
            Timber.d("Trying to delete: %s", items);
            String whereClause = String.format("_id in (%s);", ids);
            mDb.delete(TodoContract.ItemEntry.TABLE_NAME, whereClause, args);
            Timber.d("Successfully deleted: %s", items);

        } catch (IllegalStateException e) {
            Timber.e(e, "Error deleting items: %s", items);
        }

    }
}
