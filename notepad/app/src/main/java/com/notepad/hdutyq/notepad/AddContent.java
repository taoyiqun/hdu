package com.notepad.hdutyq.notepad;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContent extends Activity {
    private EditText edittext;
    private MySqlHelper mysqkhelper;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        edittext = this.findViewById(R.id.text);
        mysqkhelper = new MySqlHelper(this);
        database = mysqkhelper.getWritableDatabase();
    }
    public void save(View v) {
        DbAdd();
        finish();
    }
    public void cancle(View v) {
        edittext.setText("");
        finish();
    }
    public void DbAdd() {
        ContentValues cv = new ContentValues();
        cv.put(mysqkhelper.CONTENT,edittext.getText().toString());
        cv.put(mysqkhelper.TIME,getTime());
        database.insert(mysqkhelper.TABLE_NAME,null,cv);
    }
    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String str = sdf.format(date);
        return str;
    }

}
