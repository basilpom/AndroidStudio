package com.cookandroid.newsheadline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SecondActivity extends AppCompatActivity {
    WebView webView;
    String url;

    // prevent using internal browser when click the link
    class CookWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        webView = findViewById(R.id.webView);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        Log.d("!!! URL !!!", url);

        webView.setWebViewClient(new CookWebViewClient());
        WebSettings webSet = webView.getSettings();
        webSet.setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
