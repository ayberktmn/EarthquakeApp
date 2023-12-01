package com.ayberk.earthquakeapp.model

data class Airport(
    val code: String,
    val coordinates: Coordinates,
    val distance: Double,
    val name: String
)