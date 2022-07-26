package com.example.task1.data.pojo


data class ModelBodyResponse(
    val ok: Boolean,
    val result: ModelOptions
)

data class ModelOptions(
    val code: String,
    val full_share_link: String,
    val full_short_link: String,
    val full_short_link2: String,
    val full_short_link3: String,
    val original_link: String,
    val share_link: String,
    var short_link: String,
    val short_link2: String,
    val short_link3: String
)