package com.ehedgehog.android.catsgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ehedgehog.android.catsgallery.model.Breed;
import com.ehedgehog.android.catsgallery.model.CatImage;
import com.squareup.picasso.Picasso;

public class BreedDetailsFragment extends Fragment {

    private static final String TAG = "BreedDetailsFragment";
    private static final String ARG_BREED = "Breed";
    private static final String ARG_IMAGE = "CatImage";

    private Breed mBreed;
    private CatImage mCatImage;

    private ImageView mBreedImage;
    private TextView mDescription;
    private TextView mAltNames;
    private TextView mOrigin;
    private TextView mTemperament;
    private TextView mLifeSpan;
    private TextView mWeight;
    private TextView mAdaptability;
    private TextView mAffectionLevel;
    private TextView mEnergyLevel;
    private TextView mIntelligence;
    private TextView mStrangerFriendly;

    public static BreedDetailsFragment newInstance(Breed breed, CatImage catImage) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BREED, breed);
        args.putSerializable(ARG_IMAGE, catImage);

        BreedDetailsFragment fragment = new BreedDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mBreed = (Breed) getArguments().getSerializable(ARG_BREED);
            mCatImage = (CatImage) getArguments().getSerializable(ARG_IMAGE);
            Log.i(TAG, mCatImage == null ? "image is null" : "image not null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breed_details, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(mBreed.getName());

        mBreedImage = view.findViewById(R.id.breed_details_image);
        Picasso.get().load(mCatImage.getUrl()).fit().centerCrop().into(mBreedImage);
        mDescription = view.findViewById(R.id.breed_description);
        mDescription.setText(mBreed.getDescription());
        mAltNames = view.findViewById(R.id.breed_alt_names);
        mAltNames.setText(mBreed.getAltNames());
        mOrigin = view.findViewById(R.id.breed_origin);
        mOrigin.setText(mBreed.getOrigin());
        mTemperament = view.findViewById(R.id.breed_temperament);
        mTemperament.setText(mBreed.getTemperament());
        mLifeSpan = view.findViewById(R.id.breed_life_span);
        mLifeSpan.setText(mBreed.getLifeSpan());
        mWeight = view.findViewById(R.id.breed_weight);
        mWeight.setText(mBreed.getWeight().getMetric());
        mAdaptability = view.findViewById(R.id.breed_adaptability);
        mAdaptability.setText(String.valueOf(mBreed.getAdaptability()));
        mAffectionLevel = view.findViewById(R.id.breed_affection_level);
        mAffectionLevel.setText(String.valueOf(mBreed.getAffectionLevel()));
        mEnergyLevel = view.findViewById(R.id.breed_energy_level);
        mEnergyLevel.setText(String.valueOf(mBreed.getEnergyLevel()));
        mIntelligence = view.findViewById(R.id.breed_intelligence);
        mIntelligence.setText(String.valueOf(mBreed.getIntelligence()));
        mStrangerFriendly = view.findViewById(R.id.breed_stranger_friendly);
        mStrangerFriendly.setText(String.valueOf(mBreed.getStrangerFriendly()));

        return view;
    }
}
