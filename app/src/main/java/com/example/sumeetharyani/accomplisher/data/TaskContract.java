package com.example.sumeetharyani.accomplisher.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class TaskContract {
    public static final String CONTENT_AUTHORITY = "com.example.sumeetharyani.accomplisher";
    public static final String PATH_TASK = "tasks";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class TaskEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TASK);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;
        public static final String TABLE_NAME = "tasks";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TASK_TITLE = "task_title";
        public static final String COLUMN_TASK_DESCRIPTION = "description";
        public static final String COLUMN_TASK_PRIORITY = "priority";
        public static final String COLUMN_TASK_DATE = "date";
        public static final String COLUMN_TASK_COLOR = "color";
        public static final String COLUMN_TASK_STATUS = "task_completed";
        public static final String COLUMN_TASK_CATEGORY = "category";


    }
}
