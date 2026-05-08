package com.example.dbzapi.viewModel

import com.example.dbzapi.model.Character
import com.example.dbzapi.model.Planet

sealed class DragonBallState {

    object Loading : DragonBallState()

    data class Success(val characters: List<Character>, val planets: List<Planet>) : DragonBallState()
    data class Error(val message: String) : DragonBallState()
}