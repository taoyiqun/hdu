package com.example.hdutyq.renzheng;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class talk extends AppCompatActivity {
    private List<Msg> msgList=new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView recyclerView;
    private MsgAdapter  adapter;
    private Msg chat=null;
    private Date    current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        inputText=findViewById(R.id.input_text);
        send=findViewById(R.id.send);
        recyclerView=findViewById(R.id.msg_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        adapter=new MsgAdapter(msgList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        current=new Date();
        ActionBar   actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content=inputText.getText().toString();
                if(!"".equals(content)){
                        Msg msg=new Msg();
                        msg.setType(Msg.TYPE_SENT);
                        msg.setContent(content);
                        msg.setDate(new Date());
                        msgList.add(msg);
                        adapter.notifyDataSetChanged();
                        inputText.setText("");
                        recyclerView.scrollToPosition(msgList.size()-1);
                        new Thread(){
                        public void run(){
                            Msg chat = null;
                            try {
                                chat = HttpUtils.sendMsg(content);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Message message = new Message();
                            message.what = 0x1;
                            message.obj = chat;
                            handler.sendMessage(message);
                        };
                    }.start();
                }else {
                    Toast.makeText(talk.this, "对不起，您还未发送任何消息",
                            Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });
    }
    private Handler handler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            if (msg.what== 0x1){
                if(msg.obj!=null){
                    chat=(Msg)msg.obj;
                }
                msgList.add(chat);
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(msgList.size()-1);
            }
        };
    };
}
