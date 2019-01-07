package com.notepad.hdutyq.notepad;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
    private Button  button;
    private ListView    listView;
    private Intent intent;
    private MyAdapter myAdapter;
    private MySqlHelper mySqlHelper;
    private Cursor cursor;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=this.findViewById(R.id.list);
        mySqlHelper = new MySqlHelper(this);
        database=mySqlHelper.getReadableDatabase();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent intent = new Intent(MainActivity.this,ShowContent.class);
                intent.putExtra(mySqlHelper.ID,cursor.getInt(cursor.getColumnIndex(mySqlHelper.ID)));
                intent.putExtra(mySqlHelper.CONTENT,cursor.getString(cursor.getColumnIndex(mySqlHelper.CONTENT)));
                intent.putExtra(mySqlHelper.TIME,cursor.getString(cursor.getColumnIndex(mySqlHelper.TIME)));
                startActivity(intent);
            }
        });
    }
    public void add(View v) {
        intent = new Intent(MainActivity.this,AddContent.class);
        startActivity(intent);
    }
    public void selectDb() {
        cursor = database.query
                (mySqlHelper.TABLE_NAME,null,null,null,null,null,null);
        myAdapter = new MyAdapter(this,cursor);
        listView.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDb();
    }

}
