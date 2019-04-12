package com.ehedgehog.android.catsgallery.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class Breed extends RealmObject implements Serializable {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("temperament")
    private String mTemperament;
    @SerializedName("life_span")
    private String mLifeSpan;
    @SerializedName("alt_names")
    private String mAltNames;
    @SerializedName("wikipedia_url")
    private String mWikipediaUrl;
    @SerializedName("origin")
    private String mOrigin;
    @SerializedName("weight")
    private Weight mWeight;
    @SerializedName("adaptability")
    private int mAdaptability;
    @SerializedName("affection_level")
    private int mAffectionLevel;
    @SerializedName("energy_level")
    private int mEnergyLevel;
    @SerializedName("intelligence")
    private int mIntelligence;
    @SerializedName("stranger_friendly")
    private int mStrangerFriendly;

    public Breed() {
    }

    public Breed(String id, String name, String description, String temperament, String lifeSpan, String altNames,
                 String wikipediaUrl, String origin, Weight weight, int adaptability,
                 int affectionLevel, int energyLevel, int intelligence, int strangerFriendly) {
        mId = id;
        mName = name;
        mDescription = description;
        mTemperament = temperament;
        mLifeSpan = lifeSpan;
        mAltNames = altNames;
        mWikipediaUrl = wikipediaUrl;
        mOrigin = origin;
        mWeight = weight;
        mAdaptability = adaptability;
        mAffectionLevel = affectionLevel;
        mEnergyLevel = energyLevel;
        mIntelligence = intelligence;
        mStrangerFriendly = strangerFriendly;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTemperament() {
        return mTemperament;
    }

    public void setTemperament(String temperament) {
        mTemperament = temperament;
    }

    public String getLifeSpan() {
        return mLifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        mLifeSpan = lifeSpan;
    }

    public String getAltNames() {
        return mAltNames;
    }

    public void setAltNames(String altNames) {
        mAltNames = altNames;
    }

    public String getWikipediaUrl() {
        return mWikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        mWikipediaUrl = wikipediaUrl;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String origin) {
        mOrigin = origin;
    }

    public Weight getWeight() {
        return mWeight;
    }

    public void setWeight(Weight weight) {
        mWeight = weight;
    }

    public int getAdaptability() {
        return mAdaptability;
    }

    public void setAdaptability(int adaptability) {
        mAdaptability = adaptability;
    }

    public int getAffectionLevel() {
        return mAffectionLevel;
    }

    public void setAffectionLevel(int affectionLevel) {
        mAffectionLevel = affectionLevel;
    }

    public int getEnergyLevel() {
        return mEnergyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        mEnergyLevel = energyLevel;
    }

    public int getIntelligence() {
        return mIntelligence;
    }

    public void setIntelligence(int intelligence) {
        mIntelligence = intelligence;
    }

    public int getStrangerFriendly() {
        return mStrangerFriendly;
    }

    public void setStrangerFriendly(int strangerFriendly) {
        mStrangerFriendly = strangerFriendly;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
