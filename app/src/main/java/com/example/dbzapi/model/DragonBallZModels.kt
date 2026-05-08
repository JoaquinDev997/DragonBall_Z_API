package com.example.dbzapi.model

data class BaseResponsive<T>(
    val items: List<T>,
    val meta: Meta? = null
)

data class Meta(
    val totalItems: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int
)
data class Character(
    val id: Int,
    val name: String,
    val race: String,
    val gender: String,
    val ki: String,
    val maxKi: String,
    val description: String,
    val affiliation: String,
    val image: String,
    val originPlanet: Planet? = null,
    val transformations: List<Transformation>? = emptyList()
)

data class Planet(
    val id: Int,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String
)

data class Transformation(
    val id: Int,
    val name: String,
    val ki: String,
    val image: String
)