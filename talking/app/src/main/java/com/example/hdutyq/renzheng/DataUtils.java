package com.example.hdutyq.renzheng;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return df.format(date);
    }
}
