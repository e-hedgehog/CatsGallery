package com.ehedgehog.android.catsgallery;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ehedgehog.android.catsgallery.model.Breed;
import com.ehedgehog.android.catsgallery.model.CatImage;

public class BreedDetailsActivity extends SingleFragmentActivity {

    private static final String EXTRA_BREED = "com.ehedgehog.android.catsgallery.breed";
    private static final String EXTRA_IMAGE = "com.ehedgehog.android.catsgallery.cat_image";

    public static Intent newIntent(Context context, Breed breed, CatImage catImage) {
        Intent intent = new Intent(context, BreedDetailsActivity.class);
        intent.putExtra(EXTRA_BREED, breed);
        intent.putExtra(EXTRA_IMAGE, catImage);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Breed breed = (Breed) getIntent().getSerializableExtra(EXTRA_BREED);
        CatImage catImage = (CatImage) getIntent().getSerializableExtra(EXTRA_IMAGE);
        return BreedDetailsFragment.newInstance(breed, catImage);
    }
}
