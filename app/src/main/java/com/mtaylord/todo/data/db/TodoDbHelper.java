package com.mtaylord.todo.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todo.db";

    public TodoDbHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    public static final String CREATE_ITEM_TABLE = "CREATE TABLE " +
            TodoContract.ItemEntry.TABLE_NAME + " (" +
            TodoContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TodoContract.ItemEntry.COLUMN_NAME + " TEXT," +
            TodoContract.ItemEntry.COLUMN_COMPLETE + " INTEGER DEFAULT 0," +
            TodoContract.ItemEntry.COLUMN_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            TodoContract.ItemEntry.COLUMN_UPDATED + " DATETIME DEFAULT CURRENT_TIMESTAMP)";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
        sqLiteDatabase.execSQL("INSERT INTO item (name, complete) VALUES ('testing', 1)");
        sqLiteDatabase.execSQL("INSERT INTO item (name, complete) VALUES ('testing', 0)");
        sqLiteDatabase.execSQL("INSERT INTO item (name, complete) VALUES ('testing', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
