package com.ehedgehog.android.catsgallery.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class CatImage extends RealmObject implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("url")
    private String mUrl;

    private String mBreedId;

    public CatImage(String id, String url, String breedId) {
        mId = id;
        mUrl = url;
        mBreedId = breedId;
    }

    public CatImage(String id, String url) {
        mId = id;
        mUrl = url;
    }

    public CatImage() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getBreedId() {
        return mBreedId;
    }

    public void setBreedId(String breedId) {
        mBreedId = breedId;
    }
}
