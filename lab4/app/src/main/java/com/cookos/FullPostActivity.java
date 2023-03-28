package com.cookos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.style.ClickableSpan;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FullPostActivity extends AppCompatActivity {

    private TextView title;
    private TextView description;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_post);

        var createList = (CreateList)getIntent().getParcelableExtra(getString(R.string.cellExtra));

        title = findViewById(R.id.title);
        description = findViewById(R.id.desription);
        image = findViewById(R.id.img);

        title.setText(createList.getImage_title());
        description.setText(new SpannableString(createList.getDescription()));
        image.setImageBitmap(createList.getImage_URI());

        description.setLinksClickable(true);
        description.setOnClickListener(v -> {
            Toast.makeText(this, "adsds", Toast.LENGTH_SHORT).show();
            var textView = (TextView) v;
            var spannable = (SpannedString)textView.getText();
            var clickableSpans = spannable.getSpans(textView.getSelectionStart(), textView.getSelectionEnd(), ClickableSpan.class);

            if (clickableSpans.length > 0) {
                String url = spannable.subSequence(spannable.getSpanStart(clickableSpans[0]), spannable.getSpanEnd(clickableSpans[0])).toString();
                var intent = new Intent(this, WebActivity.class);
                intent.putExtra("URL", url);
                startActivity(intent);
            }
        });
    }
}