package com.notepad.hdutyq.notepad;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ShowContent extends Activity {

    private TextView mTextview;
    private TextView time;
    private MySqlHelper mySqlHelper;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        mTextview =this.findViewById(R.id.showtext);
        time = this.findViewById(R.id.showtime);
        mySqlHelper = new MySqlHelper(this);
        database = mySqlHelper.getWritableDatabase();
        mTextview.setText(getIntent().getStringExtra(MySqlHelper.CONTENT));
        time.setText(getIntent().getStringExtra(MySqlHelper.TIME));
    }
    public void delete(View v) {
        int id = getIntent().getIntExtra(MySqlHelper.ID,0);
        database.delete(MySqlHelper.TABLE_NAME," note_id = " + id,null);
        finish();

    }
    public void goBack(View v) {
        finish();
    }

}
