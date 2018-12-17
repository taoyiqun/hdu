package com.example.hdutyq.renzheng;

import java.util.Date;

public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SENT=1;
    private String content;
    private int type;
    private Date    date;
    public Msg(){

    }

    public Msg(String   content,int type){
        this.content=content;
        this.type=type;
        this.date=new Date();
    }

    public String getContent() {
        return content;
    }


    public void setType(int type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;

    }

    public void setContent(String content) {

        this.content = content;
    }

    public int getType() {
        return type;
    }

}
