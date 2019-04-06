package com.ehedgehog.android.catsgallery;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ehedgehog.android.catsgallery.model.Breed;

import java.util.Locale;

public class BreedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mBreedImage;
    private TextView mBreedTitleTextView;

    private Breed mBreed;

    public BreedHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mBreedImage = itemView.findViewById(R.id.cats_breed_image);
        mBreedTitleTextView = itemView.findViewById(R.id.cats_breed_title);
    }

    public void bind(Breed breed, int position) {
        mBreed = breed;

        mBreedImage.setImageResource(R.mipmap.ic_launcher);
        mBreedTitleTextView.setText(String.format(Locale.getDefault(),"Breed %d", position));
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent i = BreedDetailsActivity.newIntent(context, mBreed);
        context.startActivity(i);
    }
}
