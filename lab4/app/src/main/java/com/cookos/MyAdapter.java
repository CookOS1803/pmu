package com.cookos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final ImageView img;
        private final TextView description;
        private CreateList createList = null;
        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            img = view.findViewById(R.id.img);
            description = view.findViewById(R.id.desription);
        }
    }

    private ArrayList<CreateList> galleryList;
    private GalleryFragment fragment;

    public MyAdapter(GalleryFragment fragment, ArrayList<CreateList> galleryList) {
        this.galleryList = galleryList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int pos) {
        viewHolder.createList = galleryList.get(pos);

        viewHolder.title.setText(galleryList.get(pos).getImage_title());
        viewHolder.description.setText(galleryList.get(pos).getDescription());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageBitmap(galleryList.get(pos).getImage_URI());
        viewHolder.img.setOnClickListener(view -> {
            var intent = new Intent(fragment.getActivity(), FullPostActivity.class);
            intent.putExtra(fragment.getString(R.string.cellExtra), viewHolder.createList);
            fragment.startActivity(intent);
        });
        /*viewHolder.img.setOnClickListener(v -> {
            try {
                int i = viewHolder.getAdapterPosition();

                if (fragment.getMediaPlayer().isPlaying()) {
                    if (fragment.currentlyPlaying == i) {
                        Toast.makeText(fragment.getActivity(), "PAUSING", Toast.LENGTH_SHORT).show();
                        fragment.getMediaPlayer().pause();
                    }
                    else {
                        fragment.currentlyPlaying = i;
                        fragment.getMediaPlayer().reset();
                        fragment.getMediaPlayer().setDataSource(fragment.getApplicationContext(), galleryList.get(i).getAudio_uri());
                        fragment.getMediaPlayer().prepare();
                        fragment.getMediaPlayer().start();
                    }
                } else {
                    if (fragment.currentlyPlaying == i) {
                        Toast.makeText(fragment.getActivity(), "RESUMING", Toast.LENGTH_SHORT).show();
                        fragment.getMediaPlayer().start();
                    }
                    else {
                        fragment.currentlyPlaying = i;
                        fragment.getMediaPlayer().reset();
                        fragment.getMediaPlayer().setDataSource(fragment.getApplicationContext(), galleryList.get(i).getAudio_uri());
                        fragment.getMediaPlayer().prepare();
                        fragment.getMediaPlayer().start();
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }
}
