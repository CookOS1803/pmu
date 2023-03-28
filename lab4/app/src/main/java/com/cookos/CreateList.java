package com.cookos;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class CreateList implements Parcelable {
    public static final Parcelable.Creator<CreateList> CREATOR = new Parcelable.Creator<>() {
        @Override
        public CreateList createFromParcel(Parcel parcel) {
            return new CreateList(parcel);
        }

        @Override
        public CreateList[] newArray(int i) {
            return new CreateList[i];
        }
    };

    private String image_title;
    private String description;
    private Bitmap image_uri;
    private Uri audio_uri;

    public CreateList() {}

    public Uri getAudio_uri() {
        return audio_uri;
    }

    public void setAudio_uri(Uri audio_uri) {
        this.audio_uri = audio_uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public Bitmap getImage_URI() {
        return image_uri;
    }

    public void setImage_URI(Bitmap uri) {
        this.image_uri = uri;
    }

    public CreateList(Parcel in) {
        image_title = in.readString();
        description = in.readString();
        image_uri = in.readParcelable(Bitmap.class.getClassLoader());
        audio_uri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(image_title);
        parcel.writeString(description);
        parcel.writeParcelable(image_uri, i);
        parcel.writeParcelable(audio_uri, i);
    }
}
