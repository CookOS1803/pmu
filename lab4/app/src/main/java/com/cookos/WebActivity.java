package com.cookos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toast.makeText(this, "sadddddddd", Toast.LENGTH_SHORT).show();
        var url = getIntent().getStringExtra("URL");

        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);
    }
}