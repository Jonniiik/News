package com.eugene.newstest.Retrofit;

import com.eugene.newstest.model.RootObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRetrofitClient {
    @GET("everything?q=android")
    Observable<RootObject> getNews(@Query("from") String from,
                                   @Query("sortBy") String sortBy,
                                   @Query("apiKey") String apiKey,
                                   @Query("page") int page);
}
