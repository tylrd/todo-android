package com.mtaylord.todo.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDbHelper extends SQLiteOpenHelper {

    private static TodoDbHelper instance;

    public static final String DATABASE_NAME = "todo.db";

    public TodoDbHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    public static final String CREATE_ITEM_TABLE = "CREATE TABLE " +
            TodoContract.ItemEntry.TABLE_NAME + " (" +
            TodoContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TodoContract.ItemEntry.COLUMN_LIST_ID + " INTEGER," +
            TodoContract.ItemEntry.COLUMN_NAME + " TEXT," +
            TodoContract.ItemEntry.COLUMN_COMPLETE + " INTEGER DEFAULT 0," +
            TodoContract.ItemEntry.COLUMN_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            TodoContract.ItemEntry.COLUMN_UPDATED + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY(" + TodoContract.ItemEntry.COLUMN_LIST_ID + ") REFERENCES " +
            TodoContract.ListEntry.TABLE_NAME + "(" + TodoContract.ListEntry._ID +"))";


    public static final String CREATE_LIST_TABLE = "CREATE TABLE " +
            TodoContract.ListEntry.TABLE_NAME + " (" +
            TodoContract.ListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TodoContract.ListEntry.COLUMN_NAME + " TEXT," +
            TodoContract.ListEntry.COLUMN_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            TodoContract.ListEntry.COLUMN_UPDATED + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    public static synchronized TodoDbHelper getInstance(Context context, int version)  {
        if (instance == null) {
            instance = new TodoDbHelper(context, version);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LIST_TABLE);
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
