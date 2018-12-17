package com.example.hdutyq.renzheng;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.xml.transform.Result;

public class HttpUtils {
    public static Msg   sendMsg(String  Message) throws IOException {
        Msg msg=new Msg();
        String  gsonResult=daGet(Message);
        Gson    gson=new Gson();
        com.example.hdutyq.renzheng.Result result=null;
        if(gsonResult!=null){
            try {
                result=gson.fromJson(gsonResult, com.example.hdutyq.renzheng.Result.class);
                msg.setContent(result.getText());
            }catch (Exception   e){
                msg.setContent("服务器繁忙");
            }
        }
        msg.setDate(new Date());
        msg.setType(com.example.hdutyq.renzheng.Msg.TYPE_RECEIVED);
        return msg;

    }
    private static String   setParmat(String    msg){
        String url="";
        try{
            url=Config.URL_KEY+"?"+"key="+Config.APP_KEY+"&info="+ URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }




    public static String    daGet(String    msg) throws IOException {
        String result="";
        String url=setParmat(msg);
        InputStream is=null;
        ByteArrayOutputStream   baos=null;
        URL urls=new URL(url);
        HttpURLConnection   connection= (HttpURLConnection) urls.openConnection();
        connection.setReadTimeout(5*1000);
        connection.setConnectTimeout(5*1000);
        connection.setRequestMethod("GET");
        is=connection.getInputStream();
        baos=new ByteArrayOutputStream();
        int len=-1;
        byte[]  buff=new byte[1024];
        while ((len=is.read(buff))!=-1){
            baos.write(buff,0,len);
        }
        baos.flush();
        result=new String(baos.toByteArray());
        if(is!=null){
            is.close();
        }
        if(baos!=null){
            baos.close();
        }
        return result;

    }










}
