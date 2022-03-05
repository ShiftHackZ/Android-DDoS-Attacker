package com.shifthackz.android.attacker.attack.http_flood

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface HttpFloodService {

    @GET("/")
    @Headers("Content-Type: application/json; charset=utf-8")
    fun request(): Call<ResponseBody>
}