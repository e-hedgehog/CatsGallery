package com.ehedgehog.android.catsgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import com.ehedgehog.android.catsgallery.model.CatImage;
import com.ehedgehog.android.catsgallery.network.ApiFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class CatsImagesFragment extends BaseFragment {

    private static final String TAG = "CatsImagesFragment";

    private static final int ITEMS_PER_PAGE = 20;

    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private ImagesAdapter mAdapter;

    private int mPaginationCount;
    private int mPaginationPages;
    private int mPaginationPage;

    private boolean isLoading;

    private Disposable mCatsSubscription;

    public static CatsImagesFragment newInstance() {
        return new CatsImagesFragment();
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
        View view = inflater
                .inflate(R.layout.fragment_cats_gallery_child, container, false);

        mSwipeRefresh = view.findViewById(R.id.cats_gallery_swipe_refresh);
        setupSwipeRefresh(mSwipeRefresh);

        mRecyclerView = view.findViewById(R.id.cats_gallery_recycler_view);
        setupRecyclerView(mRecyclerView);

        mProgressBar = view.findViewById(R.id.cats_gallery_progress_bar);

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
                        loadImages();
                    }
                }
            }
        });

        recyclerView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        recyclerView.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                        int columnCount = recyclerView.getWidth() / 300;
                        layoutManager.setSpanCount(columnCount);
                    }
                });
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
                loadImages();
            }
            swipeRefresh.setRefreshing(false);
        });
    }

    private void loadImages() {
        mProgressBar.setVisibility(View.VISIBLE);
        mCatsSubscription = ApiFactory.buildCatsService()
                .getCatImages(ITEMS_PER_PAGE, mPaginationPage)
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
                .flatMap(images -> {
                    Realm.init(getActivity());
                    Realm realmInstance = Realm.getDefaultInstance();
                    realmInstance.executeTransaction(realm -> {
                        if (mPaginationPage == 0)
                            realm.delete(CatImage.class);
                        realm.insert(images);
                    });
                    realmInstance.close();
                    return Observable.just(images);
                })
                .onErrorResumeNext(throwable -> {
                    Realm.init(getActivity());
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<CatImage> results = realm.where(CatImage.class).findAll();
                    List<CatImage> images = realm.copyFromRealm(results);
                    realm.close();
                    return Observable.just(images);
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
            if (mPaginationPage == 0) {
                adapter.setImages(images);
                adapter.notifyDataSetChanged();
            } else {
                adapter.addAll(images);
                adapter.notifyItemRangeInserted(
                        mPaginationPage * ITEMS_PER_PAGE, ITEMS_PER_PAGE);
            }
        }

        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
    }
}
