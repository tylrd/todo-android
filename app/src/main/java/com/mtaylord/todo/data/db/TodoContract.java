package com.mtaylord.todo.data.db;

import android.provider.BaseColumns;

public class TodoContract {

    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COMPLETE = "complete";
        public static final String COLUMN_CREATED = "created_ts";
        public static final String COLUMN_UPDATED = "updated_ts";
    }

}
