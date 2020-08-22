package com.alexkudin.chackjokes.server

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerApi {

    @GET("jokes/random/{count}")
    fun getJokesByName(
        @Path("count") count: Int,
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String
    ): Single<JsonObject>
}