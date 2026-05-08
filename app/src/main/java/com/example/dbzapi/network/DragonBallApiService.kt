package com.example.dbzapi.network

import com.example.dbzapi.model.BaseResponsive
import com.example.dbzapi.model.Character
import com.example.dbzapi.model.Planet
import retrofit2.http.GET
import retrofit2.http.Query

interface DragonBallApiService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("name") name : String? = null,
        @Query("race") race : String? = null
    ): BaseResponsive<Character>

    @GET("planets")
    suspend fun getPlanets(): BaseResponsive<Planet>
}
