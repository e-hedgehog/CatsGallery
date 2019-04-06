package com.ehedgehog.android.catsgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehedgehog.android.catsgallery.model.CatImage;
import com.ehedgehog.android.catsgallery.network.ApiFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CatsImagesFragment extends Fragment {

    private static final String TAG = "CatsImagesFragment";

    private RecyclerView mRecyclerView;
    private ImagesAdapter mAdapter;

    private int mPaginationCount;

    private Disposable mCatsSubscription;

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

        loadImages();

        return view;
    }

    @Override
    public void onPause() {
        if (mCatsSubscription != null)
            mCatsSubscription.dispose();

        super.onPause();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void loadImages() {
        mCatsSubscription = ApiFactory.buildCatsService()
                .getCatImages(20, 1)
                .map(listResponse -> {
                    String countString = listResponse.headers().get("pagination-count");
                    if (countString != null)
                        mPaginationCount = Integer.parseInt(countString);

                    return listResponse.body();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateImages, throwable ->
                        Log.e(TAG, "Something is wrong", throwable));

    }

    private void updateImages(List<CatImage> images) {
        ImagesAdapter adapter = (ImagesAdapter) mRecyclerView.getAdapter();
        if (adapter == null) {
            adapter = new ImagesAdapter(getActivity(), images);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.setImages(images);
            adapter.notifyDataSetChanged();
        }
    }
}
