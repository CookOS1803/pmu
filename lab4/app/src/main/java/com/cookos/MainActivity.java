package com.cookos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String LOGIN = "admin@gmail.com";
    private static final String PASSWORD = "admin";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 6;

    private HashMap<String, byte[]> accounts;
    public HashMap<String, byte[]> getAccounts() {
        return accounts;
    }

    private GalleryFragment currentGallery = null;
    public GalleryFragment getCurrentGallery() {
        return currentGallery;
    }
    public void setCurrentGallery(GalleryFragment currentGallery) {
        this.currentGallery = currentGallery;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {

            accounts = new HashMap<>();
            accounts.put(LOGIN, HashPassword.getHash(PASSWORD));

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, LoginFragment.class, null)
                    .commit();
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    public void replaceFragments(Class<? extends Fragment> fragmentClass, String name) {

        // Insert the fragment by replacing any existing fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragmentClass, null)
                .addToBackStack(name)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, do something with external storage
            } else {
                // Permission denied, handle the user's response
            }
        }
    }
}