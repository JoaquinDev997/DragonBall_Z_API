package com.example.dbzapi.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbzapi.model.Character
import com.example.dbzapi.repository.DragonBallZRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DragonBallZViewModel : ViewModel() {
    private val repository = DragonBallZRepository()

    private val _state = MutableStateFlow<DragonBallState>(DragonBallState.Loading)
    val state: StateFlow<DragonBallState> = _state

    var selectedCharacter by mutableStateOf<Character?>(null)
        private set

    init {
        loadData()
    }

    fun selectCharacter(character: Character) {
        selectedCharacter = character
    }
    fun loadData() {
        viewModelScope.launch {
            _state.value = DragonBallState.Loading

            try{
                val charR = repository.getCharacters( )
                val planetR = repository.getPlanets()
                _state.value = DragonBallState.Success(

                    characters = charR.items,
                    planets = planetR.items
                )

            }catch (e : Exception){
                _state.value = DragonBallState.Error("Error al cargar a los Guerreros Z: ${e.message}")
            }

        }

    }

}


