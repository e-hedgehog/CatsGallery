package com.ehedgehog.android.catsgallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehedgehog.android.catsgallery.model.Breed;

import java.util.List;

public class BreedsAdapter extends RecyclerView.Adapter<BreedHolder> {

    private Context mContext;
    private List<Breed> mBreeds;

    public BreedsAdapter(Context context, List<Breed> breeds) {
        mContext = context;
        mBreeds = breeds;
    }

    @NonNull
    @Override
    public BreedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.cats_breed_item, viewGroup, false);
        return new BreedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedHolder breedHolder, int i) {
        breedHolder.bind(mContext, mBreeds.get(i));
    }

    @Override
    public int getItemCount() {
        return mBreeds.size();
    }

    public void setBreeds(List<Breed> breeds) {
        mBreeds = breeds;
    }

    public void addAll(List<Breed> breeds) {
        mBreeds.addAll(breeds);
    }
}
