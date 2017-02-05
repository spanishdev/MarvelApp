package com.jordic.marvelapp.classes;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.description;

/**
 * Created by J on 05/02/2017.
 */

public class Comic implements Parcelable {

    private String title, thumbnail_url, description,image_url;

    /**
     * Creates a new comic
     * @param title Title of the comic
     * @param thumbnail_url Image source of the thumbnail
     * @param description Description of the comic
     * @param image_url Image source, randomly picked from an image list which returns the webservice
     */
    public Comic(String title, String thumbnail_url, String description, String image_url)
    {
        this.title=title;
        this.thumbnail_url=thumbnail_url;
        this.description=description;
        this.image_url=image_url;
    }

    protected Comic(Parcel in) {
        title = in.readString();
        thumbnail_url = in.readString();
        description = in.readString();
        image_url = in.readString();
    }

    //PARCELABLE FUNCTIONS

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(thumbnail_url);
        parcel.writeString(description);
        parcel.writeString(image_url);
    }

    //GETTERS AND SETTERS

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

}
