package com.mtaylord.todo.data.db;

import android.provider.BaseColumns;

public class TodoContract {

    private static interface TodoBaseColumns extends BaseColumns {
        public static final String COLUMN_CREATED = "created_ts";
        public static final String COLUMN_UPDATED = "updated_ts";
    }

    public static class ItemEntry implements TodoBaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LIST_ID = "list_id";
        public static final String COLUMN_COMPLETE = "complete";
    }

    public static class ListEntry implements TodoBaseColumns {
        public static final String TABLE_NAME = "list";
        public static final String COLUMN_NAME = "name";
    }

}
