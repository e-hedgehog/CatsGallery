package com.ehedgehog.android.catsgallery.network;

import com.ehedgehog.android.catsgallery.BuildConfig;
import com.ehedgehog.android.catsgallery.model.CatImage;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CatsService {

    @Headers("X-Api-Key: " + BuildConfig.API_KEY)
    @GET("images/search")
    Observable<Response<List<CatImage>>> getCatImages(@Query("limit") int limit, @Query("page") int page);

}
