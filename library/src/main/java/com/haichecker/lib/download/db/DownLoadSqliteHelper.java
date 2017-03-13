package com.haichecker.lib.download.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-10 13:32
 */

public class DownLoadSqliteHelper extends SQLiteOpenHelper {
    public static final String DOWNLOADDBNAME = "download.db";
    public static final int DOWNLOADDBVERSION = 1;
    public static final String DB_TABLE_NAME = "download";
    public static final String DB_TABLE = "CREATE TABLE \"" + DB_TABLE_NAME + "\" (\n" +
            "    \"downLoadUrl\" TEXT NOT NULL,\n" +
            "    \"savePath\" INTEGER NOT NULL,\n" +
            "    \"_id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    \"startDownLoadTime\" INTEGER NOT NULL,\n" +
            "    \"endDownLoadTime\" INTEGER NOT NULL DEFAULT (0),\n" +
            "    \"fileSize\" INTEGER NOT NULL DEFAULT (0),\n" +
            "    \"currState\" INTEGER NOT NULL DEFAULT (5)\n" +
            ");";

    public DownLoadSqliteHelper(Context context) {
        super(context, DOWNLOADDBNAME, null, DOWNLOADDBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(sqLiteDatabase);
    }
}
