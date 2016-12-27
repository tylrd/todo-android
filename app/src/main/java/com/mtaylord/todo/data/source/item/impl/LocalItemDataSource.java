package com.mtaylord.todo.data.source.item.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mtaylord.todo.data.db.TodoContract;
import com.mtaylord.todo.data.db.TodoDbHelper;
import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.source.item.ItemDataSource;
import com.mtaylord.todo.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

/**
 * Created by taylordaugherty on 12/26/16.
 */

public class LocalItemDataSource implements ItemDataSource {

    private static LocalItemDataSource INSTANCE;

    private static final String[] ITEM_PROJECTION = new String[] {
            TodoContract.ItemEntry._ID,
            TodoContract.ItemEntry.COLUMN_NAME,
            TodoContract.ItemEntry.COLUMN_LIST_ID,
            TodoContract.ItemEntry.COLUMN_COMPLETE,
            TodoContract.ItemEntry.COLUMN_CREATED,
            TodoContract.ItemEntry.COLUMN_UPDATED
    };

    private SQLiteDatabase mDb;

    private LocalItemDataSource(Context context) {
        TodoDbHelper todoDbHelper = TodoDbHelper.getInstance(context, 1);
        mDb = todoDbHelper.getWritableDatabase();
    }

    public static LocalItemDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalItemDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public Item create(Item data) {
        if (data == null) {
            return null;
        }

        Date now = new Date();

        try {
            String timeCreated = TimeUtil.toDateString(now);

            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoContract.ItemEntry.COLUMN_NAME, data.getName());
            contentValues.put(TodoContract.ItemEntry.COLUMN_LIST_ID, data.getListId());
            contentValues.put(TodoContract.ItemEntry.COLUMN_COMPLETE, data.isComplete() ? 1 : 0);
            contentValues.put(TodoContract.ItemEntry.COLUMN_CREATED, timeCreated);
            contentValues.put(TodoContract.ItemEntry.COLUMN_UPDATED, timeCreated);

            long id = mDb.insert(TodoContract.ItemEntry.TABLE_NAME, null, contentValues);

            Timber.d("Item successfully saved: %s", data);
            data.setId((int) id);
        } catch (IllegalStateException e) {
            Timber.e(e, "Error saving item %s", data.getName());
        }
        return data;
    }

    @Override
    public void delete(int id) {
        try {
            if (id <= 0) {
                return;
            }

            String whereClause = "_id = ?;";
            String[] args = {Integer.toString(id)};

            mDb.delete(TodoContract.ItemEntry.TABLE_NAME, whereClause, args);
            Timber.d("Successfully deleted item: %d", id);
        } catch (IllegalStateException e) {
            Timber.e(e, "Error deleting item: %d", id);
        }
    }

    @Override
    public void deleteAll(List<Item> data) {
        try {
            if (data == null || data.isEmpty()) {
                return;
            }

            StringBuilder builder = new StringBuilder();
            String[] args = buildArgs(data, builder);
            Timber.d("Trying to delete: %s", data);

            String whereClause = String.format("_id in (%s);", builder.toString());
            mDb.delete(TodoContract.ItemEntry.TABLE_NAME, whereClause, args);
            Timber.d("Successfully deleted: %s", data);

        } catch (IllegalStateException e) {
            Timber.e(e, "Error deleting items: %s", data);
        }
    }

    @Override
    public Item update(Item data) {
        try {
            if (data == null || data.getId() <= 0) {
                return null;
            }

            Date now = new Date();
            data.setUpdated(now);

            String whereClause = "_id = ?;";
            String[] args = {Integer.toString(data.getId())};

            ContentValues values = new ContentValues();
            values.put(TodoContract.ItemEntry.COLUMN_COMPLETE, data.isComplete());
            values.put(TodoContract.ItemEntry.COLUMN_LIST_ID, data.getListId());
            values.put(TodoContract.ItemEntry.COLUMN_NAME, data.getName());
            values.put(TodoContract.ItemEntry.COLUMN_UPDATED, TimeUtil.toDateString(data.getUpdated()));

            mDb.update(TodoContract.ItemEntry.TABLE_NAME, values, whereClause, args);

            Timber.d("Successfully updated item: %s", data);
        } catch (IllegalStateException e) {
            Timber.e(e, "Error updating item: %s", data);
        }
        return data;
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        try {
            Cursor cursor = mDb.query(TodoContract.ItemEntry.TABLE_NAME,
                    ITEM_PROJECTION, null, null, null, null, null);

            items = cursorToItems(cursor);

            if (cursor != null) {
                cursor.close();
            }

        } catch (IllegalStateException e) {
            Timber.e(e);
        }
        return items;
    }

    @Override
    public Item get(int id) {
        return null;
    }

    @Override
    public List<Item> getAllByList(int listId) {
        return null;
    }

    @Override
    public List<Item> getAllContaining(String search) {
        return null;
    }

    private String[] buildArgs(List<Item> items, StringBuilder builder) {
        String[] args = new String[items.size()];
        for (int i = 0; i < args.length; i++) {
            builder.append("?");
            if (i != args.length - 1) {
                builder.append(",");
            }
            args[i] = Integer.toString(items.get(i).getId());
        }
        return args;
    }

    @Override
    public List<Item> getAllByCompletion(int listId, boolean isComplete) {
        List<Item> items = new ArrayList<>();
        try {

            String selection = String.format("%s = ?", TodoContract.ItemEntry.COLUMN_COMPLETE);
            String[] selectionArgs = isComplete ? new String[]{"1"} : new String[]{"0"};

            Cursor cursor = mDb.query(TodoContract.ItemEntry.TABLE_NAME,
                    ITEM_PROJECTION, selection, selectionArgs, null, null, null);

            items = cursorToItems(cursor);

            if (cursor != null) {
                cursor.close();
            }

        } catch (IllegalStateException e) {
            Timber.e(e);
        }
        return items;
    }

    private List<Item> cursorToItems(Cursor cursor) {
        List<Item> items = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry._ID));
                int listId = cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_LIST_ID));
                int completed = cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_COMPLETE));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_NAME));
                String created = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_CREATED));
                String updated = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_UPDATED));

                Item item = new Item(name, completed != 0);
                item.setId(id);
                item.setListId(listId);
                item.setCreated(TimeUtil.toDate(created));
                item.setUpdated(TimeUtil.toDate(updated));
                Timber.d("Item loaded: %s", item);
                items.add(item);
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return items;
    }

}
