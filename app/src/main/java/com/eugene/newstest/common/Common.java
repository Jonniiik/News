package com.eugene.newstest.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String API_URL = "https://newsapi.org/v2/";
    public static final String API_KEY = "26eddb253e7840f988aec61f2ece2907";
    public static String from = "2019-11-25";
    public static String sortBy = "publishedAt";
    public static int page = 1;

    public static String convertUnixToDate(Date dt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm dd.MM.yyyy");
        String formatted = simpleDateFormat.format(dt);
        return formatted;
    }

}
