package com.ehedgehog.android.catsgallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehedgehog.android.catsgallery.model.CatImage;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImageHolder> {

    private Context mContext;
    private List<CatImage> mImages;

    public ImagesAdapter(Context context, List<CatImage> images) {
        mContext = context;
        mImages = images;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.cats_image_item, viewGroup, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder imageHolder, int i) {
        imageHolder.bind(mImages.get(i));
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public void setImages(List<CatImage> images) {
        mImages = images;
    }
}
