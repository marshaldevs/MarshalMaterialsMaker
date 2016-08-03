package com.marshaldevs.marshalmaterialsmaker;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IMarshalService {
    // TODO
    @POST(MarshalServiceProvider.POST_MATERIAL)
    Call<MaterialItem> postMaterial(@Body MaterialItem materialItem);

    @POST(MarshalServiceProvider.POST_PUSH_UPDATE)
    Call postUpdatePush();
}
