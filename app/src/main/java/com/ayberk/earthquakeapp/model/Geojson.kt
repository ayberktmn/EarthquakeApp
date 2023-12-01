package com.ayberk.earthquakeapp.model

data class Geojson(
    val coordinates: List<Double>,
    val type: String
)