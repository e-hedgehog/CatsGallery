package com.ehedgehog.android.catsgallery.model;

import com.google.gson.annotations.SerializedName;

public class CatImage {

    @SerializedName("id")
    private String mId;

    @SerializedName("url")
    private String mUrl;

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
}
