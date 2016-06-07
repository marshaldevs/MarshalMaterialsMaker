package com.marshaldevs.marshalmaterialsmaker;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IMarshalService {
    // TODO
    @POST(MarshalServiceProvider.POST_MATERIAL)
    Call<MaterialItem> postRating(@Body MaterialItem materialItem);
}
