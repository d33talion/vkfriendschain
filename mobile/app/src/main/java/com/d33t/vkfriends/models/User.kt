package com.d33t.vkfriends.models

import com.beust.klaxon.Json

data class Error (
    @Json(name = "error_code")
    val errorCode: String,

    @Json(name = "error_msg")
    val errorMessage: String
)

data class User (
    val id: String,

    @Json(name = "first_name")
    val firstName: String,

    @Json(name = "last_name")
    val lastName: String,

    @Json(name = "photo_50")
    val photoUrl: String
)

data class GetUserResponse(
    val error: Error?,
    val response: Array<User>?
)
