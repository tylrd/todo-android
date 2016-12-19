package com.mtaylord.todo.data.source.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mtaylord.todo.data.db.TodoContract;
import com.mtaylord.todo.data.db.TodoDbHelper;
import com.mtaylord.todo.data.source.ItemDataSource;
import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
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
                    String updated = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ItemEntry.COLUMN_UPDATED));

                    boolean itemCompleted = completed != 0;

                    if (itemCompleted == isComplete) {
                        Item item = new Item(name, itemCompleted);
                        item.setId(id);
                        item.setCreated(TimeUtil.toDate(created));
                        item.setUpdated(TimeUtil.toDate(updated));
                        Timber.d("Item loaded: %s", item);
                        items.add(item);
                    }
                }
            }

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


            String timeCreated = TimeUtil.toDateString(new Date());

            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoContract.ItemEntry.COLUMN_NAME, item.getName());
            contentValues.put(TodoContract.ItemEntry.COLUMN_COMPLETE, item.isComplete() ? 1 : 0);
            contentValues.put(TodoContract.ItemEntry.COLUMN_CREATED, timeCreated);
            contentValues.put(TodoContract.ItemEntry.COLUMN_UPDATED, timeCreated);
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
            String[] args = buildArgs(items, builder);
            Timber.d("Trying to delete: %s", items);

            String whereClause = String.format("_id in (%s);", builder.toString());
            mDb.delete(TodoContract.ItemEntry.TABLE_NAME, whereClause, args);
            Timber.d("Successfully deleted: %s", items);

        } catch (IllegalStateException e) {
            Timber.e(e, "Error deleting items: %s", items);
        }
    }

    @Override
    public void deleteItem(Item item) {
        try {
            if (item == null || item.getId() <= 0) {
                return;
            }

            String whereClause = "_id = ?;";
            String[] args = { Integer.toString(item.getId()) };

            mDb.delete(TodoContract.ItemEntry.TABLE_NAME, whereClause, args);
            Timber.d("Successfully deleted item: %s", item);
        } catch (IllegalStateException e) {
            Timber.e(e, "Error deleting item: %s", item);
        }
    }

    @Override
    public void updateItem(Item item) {
        try {
            if (item == null || item.getId() <= 0) {
                return;
            }

            String whereClause = "_id = ?;";
            String[] args = { Integer.toString(item.getId()) };

            ContentValues values = new ContentValues();
            values.put(TodoContract.ItemEntry.COLUMN_COMPLETE, item.isComplete());
            values.put(TodoContract.ItemEntry.COLUMN_NAME, item.getName());
            values.put(TodoContract.ItemEntry.COLUMN_UPDATED, TimeUtil.toDateString(item.getUpdated()));

            mDb.update(TodoContract.ItemEntry.TABLE_NAME, values, whereClause, args);

            Timber.d("Successfully updated item: %s", item);
        } catch (IllegalStateException e) {
            Timber.e(e, "Error updating item: %s", item);
        }
    }

    @Override
    public void updateItems(List<Item> items) {
        mDb.beginTransaction();
        try {
            //TODO

            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            mDb.endTransaction();
            throw e;
        } finally {
            mDb.endTransaction();
        }

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
}
