package com.cookos;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    public final int RC_ADD_POST = 3;

    private RecyclerView recyclerView;

    public Button getButton() {
        return button;
    }

    private Button button;
    private ArrayList<CreateList> createLists;
    public int currentlyPlaying = -1;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private MediaPlayer mediaPlayer = new MediaPlayer();

    private MainActivity activity;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
        activity.setCurrentGallery(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {


        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity.getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);

        createLists = new ArrayList<>();
        updateView();

        button = view.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            //activity.replaceFragments(AddPostFragment.class, "post");

            var intent = new Intent(activity, AddPostActivity.class);
            startActivityForResult(intent, RC_ADD_POST);
        });
    }

    private void updateView() {
        var adapter = new MyAdapter(this, createLists);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RC_ADD_POST:
                    var cell = (CreateList)data.getParcelableExtra(getString(R.string.cellExtra));
                    createLists.add(cell);

                    updateView();
                    break;
            }
        }

    }

    public Context getApplicationContext() {
        return activity.getApplicationContext();
    }
}