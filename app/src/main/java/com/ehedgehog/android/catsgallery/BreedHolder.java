package com.ehedgehog.android.catsgallery;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ehedgehog.android.catsgallery.model.Breed;
import com.ehedgehog.android.catsgallery.model.CatImage;
import com.ehedgehog.android.catsgallery.network.ApiFactory;
import com.squareup.picasso.Picasso;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class BreedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mBreedImage;
    private TextView mBreedTitleTextView;
    private TextView mBreedOriginTextView;

    private Breed mBreed;
    private CatImage mCatImage;
    private Context mContext;

    private Disposable mImageSubscription;

    public BreedHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mBreedImage = itemView.findViewById(R.id.cats_breed_image);
        mBreedTitleTextView = itemView.findViewById(R.id.cats_breed_title);
        mBreedOriginTextView = itemView.findViewById(R.id.cats_breed_origin);
    }

    public void bind(Context context, Breed breed) {
        mBreed = breed;
        mContext = context;

        mBreedTitleTextView.setText(breed.getName());
        mBreedOriginTextView.setText(breed.getOrigin());
        loadBreedImage(breed.getId());
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        if (mBreed != null && mCatImage != null) {
            Intent i = BreedDetailsActivity.newIntent(context, mBreed, mCatImage);
            context.startActivity(i);
        }
    }

    private void loadBreedImage(String breedId) {
        mImageSubscription = ApiFactory.buildCatsService()
                .getBreedImage(breedId)
                .flatMap(images -> {
                    CatImage image = images.get(0);
                    image.setBreedId(breedId);
                    Realm.init(mContext);
                    try (Realm realmInstance = Realm.getDefaultInstance()) {
                        CatImage result = realmInstance.where(CatImage.class)
                                .equalTo("mBreedId", breedId).findFirst();
                        if (result != null) {
                            image = realmInstance.copyFromRealm(result);
                        } else {
                            CatImage finalImage = image;
                            realmInstance.executeTransaction(realm -> realm.insert(finalImage));
                        }
                    }
                    return Observable.just(image);
                })
                .onErrorResumeNext(throwable -> {
                    Realm.init(mContext);
                    try (Realm realmInstance = Realm.getDefaultInstance()) {
                        CatImage result = realmInstance.where(CatImage.class)
                                .equalTo("mBreedId", breedId).findFirst();
                        if (result != null)
                            return Observable.just(realmInstance.copyFromRealm(result));
                        return Observable.empty();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(catImage -> {
                    mCatImage = catImage;
                    Picasso.get().load(catImage.getUrl()).fit().centerCrop().into(mBreedImage);
                    mImageSubscription.dispose();
                }, throwable -> {
                    Log.e("Holder", "Something is wrong", throwable);
                    throwable.printStackTrace();
                });
    }
}
