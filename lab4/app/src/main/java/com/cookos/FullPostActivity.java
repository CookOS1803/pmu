package com.cookos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FullPostActivity extends AppCompatActivity {

    private TextView title;
    private TextView description;
    private ImageView image;
    private FullPostActivity dis = this; // LOL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_post);

        var createList = (CreateList) getIntent().getParcelableExtra(getString(R.string.cellExtra));

        title = findViewById(R.id.title);
        description = findViewById(R.id.desription);
        image = findViewById(R.id.img);

        title.setText(createList.getImage_title());
        description.setText(createList.getDescription());
        image.setImageBitmap(createList.getImage_URI());


        Linkify.addLinks(description, Linkify.WEB_URLS);
        try {
            var spannable = (Spannable) description.getText();

            var urlSpans = spannable.getSpans(0, spannable.length(), URLSpan.class);
            for (final URLSpan urlSpan : urlSpans) {
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        String url = urlSpan.getURL();

                        var intent = new Intent(dis, WebActivity.class);
                        intent.putExtra("URL", url);
                        startActivity(intent);

                    }
                };
                int start = spannable.getSpanStart(urlSpan);
                int end = spannable.getSpanEnd(urlSpan);
                spannable.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.removeSpan(urlSpan);
            }
            description.setMovementMethod(LinkMovementMethod.getInstance());

        } catch (Exception e) {
            Toast.makeText(this, "dd", Toast.LENGTH_SHORT).show();
        }
    }
}