package com.example.hdutyq.wuziqi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ChessPanel  panel;
    private Button  myButton1;
    private Button  myButton2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        panel=findViewById(R.id.Chess_panel);
        textView=findViewById(R.id.text_view);
        myButton1=findViewById(R.id.regret);
        myButton2=findViewById(R.id.start);
        myButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel.regret();
            }
        });
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("对战模式");
                alertDialog.setMessage("请选择对战模式");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("人机对战", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        panel.AI=1;
                        textView.setText("在和机器对战");
                    }
                });
                alertDialog.setNegativeButton("双人模式", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        panel.AI=0;
                        textView.setText("双人对战");
                    }
                });
                alertDialog.create().show();
                panel.start();
            }
        });

    }

}

