package com.eugene.newstest.common;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String API_URL = "https://newsapi.org/v2/";
    public static final String API_KEY = "d1763546b8634999b9c51b4ac7fc672e";
    public static String from;
    public static String sortBy = "publishedAt";
    public static int page = 1;

    public static String convertUnixToDate(Date dt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm   dd.MM.yyyy");
        String formatted = simpleDateFormat.format(dt);
        return formatted;
    }
    public static String getData(){
        Date dateNow = new Date();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-mm-dd");
        from = simpleDateFormat.format(dateNow);
        return from;
    }

}
