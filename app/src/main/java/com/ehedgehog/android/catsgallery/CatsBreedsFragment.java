package com.ehedgehog.android.catsgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehedgehog.android.catsgallery.model.Breed;

import java.util.ArrayList;
import java.util.List;

public class CatsBreedsFragment extends Fragment {

    private static final String TAG = "CatsBreedsFragment";

    private RecyclerView mRecyclerView;
    private BreedsAdapter mAdapter;

    public static CatsBreedsFragment newInstance() {
        return new CatsBreedsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cats_gallery_child, container, false);

        mRecyclerView = view.findViewById(R.id.cats_gallery_recycler_view);
        setupRecyclerView(mRecyclerView);

        //test data
        List<Breed> breedList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            breedList.add(new Breed());
        mAdapter = new BreedsAdapter(getActivity(), breedList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
