package com.smurzik.rickstatistictesttask.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("users")
    val users: List<UserInformation>
)

@Serializable
data class UserInformation(
    @SerialName("id")
    val id: Int,
    @SerialName("sex")
    val sex: String,
    @SerialName("username")
    val username: String,
    @SerialName("isOnline")
    val isOnline: Boolean,
    @SerialName("age")
    val age: Int,
    @SerialName("files")
    val files: List<UserFile>
)

@Serializable
data class UserFile(
    @SerialName("url")
    val avatarUrl: String
)
