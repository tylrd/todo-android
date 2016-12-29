package com.mtaylord.todo.data.source.todolist.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mtaylord.todo.data.db.TodoContract;
import com.mtaylord.todo.data.db.TodoDbHelper;
import com.mtaylord.todo.data.model.Item;
import com.mtaylord.todo.data.model.TodoList;
import com.mtaylord.todo.data.source.item.ItemDataSource;
import com.mtaylord.todo.data.source.item.impl.LocalItemDataSource;
import com.mtaylord.todo.data.source.todolist.TodoListDataSource;
import com.mtaylord.todo.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

/**
 * Created by taylor on 12/29/16.
 */

public class LocalTodoListDataSource implements TodoListDataSource {

    private SQLiteDatabase mDb;
    private ItemDataSource itemDataSource;

    public LocalTodoListDataSource(TodoDbHelper todoDbHelper) {
        mDb = todoDbHelper.getWritableDatabase();
        itemDataSource = LocalItemDataSource.getInstance(todoDbHelper);
    }

    @Override
    public TodoList create(TodoList data) {
        if (data == null) {
            return null;
        }

        Date now = new Date();

        try {
            ContentValues values = new ContentValues();
            values.put(TodoContract.ListEntry.COLUMN_NAME, data.getName());
            values.put(TodoContract.ListEntry.COLUMN_CREATED, TimeUtil.toDateString(now));
            values.put(TodoContract.ListEntry.COLUMN_UPDATED, TimeUtil.toDateString(now));

            long id = mDb.insert(TodoContract.ListEntry.TABLE_NAME, null, values);
            Timber.d("Saved list: %s", data);

            data.setId((int) id);

        } catch (IllegalStateException e) {
            Timber.d("Error saving list: %s", data);
        }
        return data;
    }

    @Override
    public TodoList get(int id) {
        return null;
    }

    @Override
    public List<TodoList> getAll() {
        String[] projection = {
                TodoContract.ListEntry._ID,
                TodoContract.ListEntry.COLUMN_NAME,
                TodoContract.ListEntry.COLUMN_CREATED,
                TodoContract.ListEntry.COLUMN_UPDATED
        };

        Cursor cursor = mDb.query(TodoContract.ListEntry.TABLE_NAME, projection, null, null, null, null, null);

        List<TodoList> data = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.ListEntry._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ListEntry.COLUMN_NAME));
                String created = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ListEntry.COLUMN_CREATED));
                String updated = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.ListEntry.COLUMN_UPDATED));

                List<Item> todoItems = itemDataSource.getAllByCompletion(id, false);
                List<Item> doneItems = itemDataSource.getAllByCompletion(id, true);

                TodoList todoList = new TodoList();
                todoList.setId(id);
                todoList.setName(name);
                todoList.setDoneItems(doneItems);
                todoList.setTodoItems(todoItems);
                todoList.setCreated(TimeUtil.toDate(created));
                todoList.setUpdated(TimeUtil.toDate(updated));
                data.add(todoList);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return data;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void deleteAll(List<TodoList> data) {

    }

    @Override
    public TodoList update(TodoList data) {
        return null;
    }
}
