package com.ehedgehog.android.catsgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ehedgehog.android.catsgallery.model.Breed;
import com.ehedgehog.android.catsgallery.model.CatImage;
import com.ehedgehog.android.catsgallery.network.ApiFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class CatsBreedsFragment extends BaseFragment {

    private static final String TAG = "CatsBreedsFragment";

//    private static final int ITEMS_PER_PAGE = 20;

    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private BreedsAdapter mAdapter;

    private Disposable mBreedsSubscription;

    private int mPaginationPage;
    private int mPaginationPages;
    private int mPaginationCount;
    private boolean isLoading;

    public static CatsBreedsFragment newInstance() {
        return new CatsBreedsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnline();

        mPaginationPage = 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cats_gallery_child, container, false);

        mProgressBar = view.findViewById(R.id.cats_gallery_progress_bar);
        mRecyclerView = view.findViewById(R.id.cats_gallery_recycler_view);
        setupRecyclerView(mRecyclerView);

        mSwipeRefresh = view.findViewById(R.id.cats_gallery_swipe_refresh);
        setupSwipeRefresh(mSwipeRefresh);

        loadBreeds();

        return view;
    }

    @Override
    public void onPause() {
        if (mBreedsSubscription != null)
            mBreedsSubscription.dispose();

        super.onPause();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemsCount = layoutManager.getChildCount();
                int invisibleItemsCount = layoutManager.findFirstVisibleItemPosition();
                int totalItemsCount = layoutManager.getItemCount();
                if ((visibleItemsCount + invisibleItemsCount) >= totalItemsCount) {
                    if ((mPaginationPage < mPaginationPages) && !isLoading) {
                        Log.i(TAG, "Loading new data...");
                        ++mPaginationPage;
                        loadBreeds();
                    }
                }
            }
        });
    }

    private void loadBreeds() {
        mProgressBar.setVisibility(View.VISIBLE);
        mBreedsSubscription = ApiFactory.buildCatsService()
                .getCatsBreeds(ITEMS_PER_PAGE, mPaginationPage)
                .map(listResponse -> {
                    isLoading = true;
                    String countString = listResponse.headers().get("pagination-count");
                    if (countString != null) {
                        mPaginationCount = Integer.parseInt(countString);
                        mPaginationPages = (int) Math.ceil(mPaginationCount / ITEMS_PER_PAGE);
                        Log.i(TAG, "Pagination count = " + mPaginationCount + " | Pages = " + mPaginationPages);
                        Log.i(TAG, "Page: " + listResponse.headers().get("pagination-page") + " | " + mPaginationPage);
                    }

                    return listResponse.body();
                })
                .flatMap(breeds -> {
                    Realm.init(getActivity());
                    Realm realmInstance = Realm.getDefaultInstance();
                    realmInstance.executeTransaction(realm -> {
                        if (mPaginationPage == 0)
                            realm.delete(Breed.class);
                        realm.insert(breeds);
                    });
                    realmInstance.close();
                    return Observable.just(breeds);
                })
                .onErrorResumeNext(throwable -> {
                    Realm.init(getActivity());
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Breed> results = realm.where(Breed.class).findAll();
                    List<Breed> breeds = realm.copyFromRealm(results);
                    realm.close();
                    return Observable.just(breeds);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateBreeds, throwable ->
                        Log.e(TAG, "Something is wrong", throwable));
    }

    private void setupSwipeRefresh(SwipeRefreshLayout swipeRefresh) {
        swipeRefresh.setColorSchemeResources(
                android.R.color.black,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_dark
        );
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(true);
            mPaginationPage = 0;
            if (isOnline()) {
                loadBreeds();
            }
            swipeRefresh.setRefreshing(false);
        });
    }

    private void updateBreeds(List<Breed> breeds) {
        mAdapter = (BreedsAdapter) mRecyclerView.getAdapter();
        if (mAdapter == null) {
            mAdapter = new BreedsAdapter(getActivity(), breeds);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if (mPaginationPage == 0) {
                mAdapter.setBreeds(breeds);
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.addAll(breeds);
                mAdapter.notifyItemRangeInserted(
                        mPaginationPage * ITEMS_PER_PAGE, ITEMS_PER_PAGE);
            }
        }

        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
    }
}
