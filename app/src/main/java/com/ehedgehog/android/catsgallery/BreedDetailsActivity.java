package com.ehedgehog.android.catsgallery;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ehedgehog.android.catsgallery.model.Breed;

public class BreedDetailsActivity extends SingleFragmentActivity {

    private static final String EXTRA_BREED = "com.ehedgehog.android.catsgallery.breed";

    public static Intent newIntent(Context context, Breed breed) {
        Intent intent = new Intent(context, BreedDetailsActivity.class);
        intent.putExtra(EXTRA_BREED, breed);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Breed breed = (Breed) getIntent().getSerializableExtra(EXTRA_BREED);
        return BreedDetailsFragment.newInstance(breed);
    }
}
