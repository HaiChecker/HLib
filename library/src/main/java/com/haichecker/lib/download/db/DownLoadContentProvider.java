package com.haichecker.lib.download.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-10 13:34
 */

public class DownLoadContentProvider extends ContentProvider {
    private ContentResolver resolver;
    private DownLoadSqliteHelper helper;
    private SQLiteDatabase downloadSqlite;
    public static final String _ID = "_id";
    public static final String AUTHORITY = "com.haichecker.lib.download.db.DownLoadContentProvider";
    static final Uri DOWNLOAD_URI = Uri.parse("content://" + AUTHORITY + "download");
    private static final UriMatcher uriMatcher;
    static final int DOWNLOAD = 1;
    static final int DOWNLOAD_ID = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "download", DOWNLOAD);
        uriMatcher.addURI(AUTHORITY, "download/#", DOWNLOAD_ID);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            resolver = context.getContentResolver();
            helper = new DownLoadSqliteHelper(context);
            downloadSqlite = helper.getWritableDatabase();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        sqlBuilder.setTables(DownLoadSqliteHelper.DB_TABLE_NAME);
        if (uriMatcher.match(uri) == DOWNLOAD_ID) {
            assert uri != null;
            sqlBuilder.appendWhere(DOWNLOAD_ID + " = " + uri.getPathSegments().get(1));
        }


        if (TextUtils.isEmpty(s1))
            s1 = _ID;

        Cursor c = sqlBuilder.query(downloadSqlite, strings, s, strings1, null, null, s1);
        c.setNotificationUri(resolver, uri);


        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case DOWNLOAD:
                return "vnd.android.cursor.dir/vnd.haichecker.download";
            case DOWNLOAD_ID:
                return "vnd.android.cursor.item/vnd.haichecker.download";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowID = downloadSqlite.insert(DownLoadSqliteHelper.DB_TABLE_NAME
                , ""
                , contentValues
        );

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(DOWNLOAD_URI, rowID);
            resolver.notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case DOWNLOAD:
                count = downloadSqlite.delete(DownLoadSqliteHelper.DB_TABLE_NAME, s, strings);
                break;
            case DOWNLOAD_ID:
                String id = uri.getPathSegments().get(1);
                count = downloadSqlite.delete(DownLoadSqliteHelper.DB_TABLE_NAME, _ID + " = " + id +
                                (!TextUtils.isEmpty(s) ? "AND (" + s + ')' : ""),
                        strings
                );

                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        resolver.notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int count = -1;
        switch (uriMatcher.match(uri)) {
            case DOWNLOAD:
                count = downloadSqlite.update(DownLoadSqliteHelper.DB_TABLE_NAME, contentValues, s, strings);
                break;
            case DOWNLOAD_ID:
                String rowID = uri.getPathSegments().get(1);
                count = downloadSqlite.update(DownLoadSqliteHelper.DB_TABLE_NAME, contentValues, _ID + "=" + rowID, null);
                break;
            default:
                throw new IllegalArgumentException("Unknow URI : " + uri);
        }
        resolver.notifyChange(uri, null);
        return count;
    }
}
