package com.ehedgehog.android.catsgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehedgehog.android.catsgallery.model.CatImage;

import java.util.ArrayList;
import java.util.List;

public class CatsImagesFragment extends Fragment {

    private static final String TAG = "CatsImagesFragment";

    private RecyclerView mRecyclerView;
    private ImagesAdapter mAdapter;

    public static CatsImagesFragment newInstance() {
        return new CatsImagesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_cats_gallery_child, container, false);

        mRecyclerView = view.findViewById(R.id.cats_gallery_recycler_view);
        setupRecyclerView(mRecyclerView);

        //test data
        List<CatImage> images = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            images.add(new CatImage());
        mAdapter = new ImagesAdapter(getActivity(), images);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

    }
}
