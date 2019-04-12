package com.ehedgehog.android.catsgallery.network;

import com.ehedgehog.android.catsgallery.model.Breed;
import com.ehedgehog.android.catsgallery.model.CatImage;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatsService {

    //has a strange problem with pagination
    @GET("images/search?mime_types=jpg,png&order=desc")
    Observable<Response<List<CatImage>>> getCatImages(@Query("limit") int limit,
                                                      @Query("page") int page);

    @GET("breeds")
    Observable<Response<List<Breed>>> getCatsBreeds(@Query("limit") int limit,
                                                    @Query("page") int page);

    @GET("images/search")
    Observable<List<CatImage>> getBreedImage(@Query("breed_id") String breedId);

}
