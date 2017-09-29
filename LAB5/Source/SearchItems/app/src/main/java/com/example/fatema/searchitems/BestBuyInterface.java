package com.example.fatema.searchitems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fatema on 9/28/17.
 */

public interface BestBuyInterface
{

    @GET("products/{sku}/stores.json")
    Call<StoreOutput> getResponse(@Path("sku") String productcd, @Query("postalCode") String postalCode, @Query("apiKey") String apiKey);

    @GET("products(search={name})?format=json&show=sku&apiKey=68qthcka6yz8w2vkf7z2xqd7")
    Call<StorePlace> getResponse(@Path("name") String name);
}


