package com.ehedgehog.android.catsgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CatsGalleryFragment extends Fragment {

    private static final String TAG = "CatsGalleryFragment";

    public static CatsGalleryFragment newInstance() {
        return new CatsGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cats_gallery, container, false);

        ViewPager viewPager = view.findViewById(R.id.cats_gallery_view_pager);
        setupViewPager(viewPager);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = view.findViewById(R.id.cats_gallery_toolbar);
        activity.setSupportActionBar(toolbar);

        TabLayout tabs = view.findViewById(R.id.cats_gallery_tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        CatsGalleryPagerAdapter adapter = new CatsGalleryPagerAdapter(getChildFragmentManager());
        adapter.addFragment(CatsImagesFragment.newInstance(), getString(R.string.images_title));
        adapter.addFragment(CatsBreedsFragment.newInstance(), getString(R.string.breeds_title));
        viewPager.setAdapter(adapter);
    }
}
