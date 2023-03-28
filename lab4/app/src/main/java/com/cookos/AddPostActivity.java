package com.cookos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContentProviderCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddPostActivity extends AppCompatActivity {
    private static final int THUMBNAIL_SIZE = 200;
    public final int RC_IMAGE = 1;
    public final int RC_MUSIC = 2;

    private EditText titleField;
    private EditText descriptionField;
    private ImageView image;
    private Bitmap imageBitmap;
    private Uri musicUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        titleField = findViewById(R.id.titleField);
        descriptionField = findViewById(R.id.descriptionField);
        image = findViewById(R.id.image);

        image.setOnClickListener(v -> {
            var intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), RC_IMAGE);
        });

        //Button m = findViewById(R.id.addMusicButton);
        //m.setOnClickListener(view -> {
        //    Intent intent = new Intent();
        //    intent.setType("audio/*");
        //    //intent.setAction(Intent.ACTION_GET_CONTENT);
        //    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        //    intent.addCategory(Intent.CATEGORY_OPENABLE);
        //    startActivityForResult(Intent.createChooser(intent, "Select Audio"), RC_MUSIC);
        //});

        Button b = findViewById(R.id.addPostButton);
        b.setOnClickListener(view -> {

            var cell = new CreateList();
            cell.setImage_title(titleField.getText().toString());
            cell.setDescription(descriptionField.getText().toString());
            cell.setImage_URI(imageBitmap);
            cell.setAudio_uri(musicUri);

            var intent = new Intent();
            intent.putExtra(getString(R.string.cellExtra), cell);

            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RC_IMAGE:
                    var uri = data.getData();
                    try {
                        imageBitmap = getThumbnail(uri, this);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    image.setImageBitmap(imageBitmap);
                    break;
                case RC_MUSIC:
                    musicUri = data.getData();
                    break;

            }
        }

    }

    public static Bitmap getThumbnail(Uri uri, Context context) throws FileNotFoundException, IOException{
        InputStream input = context.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = Math.max(onlyBoundsOptions.outHeight, onlyBoundsOptions.outWidth);

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
}