package com.example.searchengineexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText keyword;
    Button btnSearch;
    WebView webNaver, webGoogle;

    class CookWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keyword = findViewById(R.id.keyword);
        btnSearch = findViewById(R.id.btnSearch);
        webNaver = findViewById(R.id.webNaver);
        webGoogle = findViewById(R.id.webGoogle);

        webNaver.setWebViewClient(new CookWebViewClient());
        webGoogle.setWebViewClient(new CookWebViewClient());

        WebSettings webNaverSet = webNaver.getSettings();
        WebSettings webGoogleSet = webGoogle.getSettings();
        webNaverSet.setJavaScriptEnabled(true);
        webGoogleSet.setJavaScriptEnabled(true);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webNaver.loadUrl("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query="+keyword.getText().toString());
                webGoogle.loadUrl("https://www.google.com/search?q="+keyword.getText().toString());
            }
        });
    }
}
