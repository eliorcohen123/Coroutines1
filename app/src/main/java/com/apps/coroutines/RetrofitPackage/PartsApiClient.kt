package com.apps.coroutines.RetrofitPackage

import com.apps.coroutines.DataPackage.JSONResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface PartsApiClient {

    @GET
    fun getPartsAsync(@Url url: String): Deferred<Response<JSONResponse>>
}
