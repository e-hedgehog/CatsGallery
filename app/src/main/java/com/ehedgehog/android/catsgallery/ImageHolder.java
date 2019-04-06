package com.ehedgehog.android.catsgallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ehedgehog.android.catsgallery.model.CatImage;
import com.squareup.picasso.Picasso;

public class ImageHolder extends RecyclerView.ViewHolder {

    private ImageView mCatImage;

    public ImageHolder(@NonNull View itemView) {
        super(itemView);

        mCatImage = itemView.findViewById(R.id.cats_image_item);
    }

    public void bind(CatImage catImage) {
        Picasso.get().load(catImage.getUrl()).into(mCatImage);
    }
}
