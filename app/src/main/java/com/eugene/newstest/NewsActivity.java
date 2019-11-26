package com.eugene.newstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    WebView webViewForNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initWebView();
    }

    private void initWebView() {
        webViewForNews = (WebView) findViewById(R.id.webViewForNews);
        Intent intent = getIntent();
        String urlNews = intent.getStringExtra("LinkNews");
        webViewForNews.setWebViewClient(new WebViewClient());
        webViewForNews.loadUrl(urlNews);
    }
}
