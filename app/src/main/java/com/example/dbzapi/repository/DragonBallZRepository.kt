package com.example.dbzapi.repository

import com.example.dbzapi.model.BaseResponsive
import com.example.dbzapi.model.Character
import com.example.dbzapi.model.Planet
import com.example.dbzapi.network.RetrofitClient

class DragonBallZRepository {
    private val api = RetrofitClient.apiService

    suspend fun getCharacters(name: String? = null, race: String? = null): BaseResponsive<Character>{

        return api.getCharacters(name,race)
    }

    suspend fun getPlanets(): BaseResponsive<Planet>{
        return api.getPlanets()

    }

}