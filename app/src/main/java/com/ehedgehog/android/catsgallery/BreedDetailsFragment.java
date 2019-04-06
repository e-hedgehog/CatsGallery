package com.ehedgehog.android.catsgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehedgehog.android.catsgallery.model.Breed;

public class BreedDetailsFragment extends Fragment {

    private static final String TAG = "BreedDetailsFragment";
    private static final String ARG_BREED = "Breed";

    private Breed mBreed;

    public static BreedDetailsFragment newInstance(Breed breed) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BREED, breed);

        BreedDetailsFragment fragment = new BreedDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mBreed = (Breed) getArguments().getSerializable(ARG_BREED);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
