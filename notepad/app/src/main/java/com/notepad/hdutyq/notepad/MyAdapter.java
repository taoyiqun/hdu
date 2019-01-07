package com.notepad.hdutyq.notepad;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private Cursor cursor;
    private LinearLayout linearLayout;
    public MyAdapter(Context context,Cursor cursor){
        this.context=context;
        this.cursor=cursor;
    }
    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        linearLayout =(LinearLayout) inflater.inflate(R.layout.item,null);
        TextView content =  linearLayout.findViewById(R.id.list_content);
        TextView time =  linearLayout.findViewById(R.id.list_time);
        cursor.moveToPosition(position);
        String dbcontext = cursor.getString(cursor.getColumnIndex("content"));
        String dbtime = cursor.getString(cursor.getColumnIndex("time"));
        content.setText(dbcontext);
        time.setText(dbtime);
        return linearLayout;
    }
}
