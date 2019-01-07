package com.notepad.hdutyq.notepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MySqlHelper extends SQLiteOpenHelper {
    private Context mContext;
    public static final String TABLE_NAME = "notes";
    public static final String CONTENT = "content";
    public static final String ID = "note_id";
    public static final String TIME = "time";

    public MySqlHelper(Context mContext) {
        super(mContext,"notes",null,1);
        this.mContext = mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table "+TABLE_NAME+" ( "+ID+" integer primary key AUTOINCREMENT, "+CONTENT
                +" TEXT NOT NULL, "+TIME+" TEXT NOT NULL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
