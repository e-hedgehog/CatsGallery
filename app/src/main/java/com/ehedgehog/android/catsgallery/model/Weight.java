package com.ehedgehog.android.catsgallery.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class Weight extends RealmObject implements Serializable {

    @SerializedName("metric")
    private String mMetric;

    public Weight() {
    }

    public Weight(String metric) {
        mMetric = metric;
    }

    public String getMetric() {
        return mMetric;
    }

    public void setMetric(String metric) {
        mMetric = metric;
    }
}
