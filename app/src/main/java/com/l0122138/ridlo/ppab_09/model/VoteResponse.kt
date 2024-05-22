package com.l0122138.ridlo.ppab_09.model

data class VoteResponse(
    val id: Int,
    val image_id: String,
    val sub_id: String,
    val created_at: String,
    val value: Int,
    val country_code: String?,
    val image: ImageInfo?
)