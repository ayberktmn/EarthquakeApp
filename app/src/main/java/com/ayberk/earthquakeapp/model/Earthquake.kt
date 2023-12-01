package com.ayberk.earthquakeapp.model

data class Earthquake(
    val desc: String,
    val httpStatus: Int,
    val metadata: Metadata,
    val result: List<Result>,
    val serverloadms: Int,
    val status: Boolean
)