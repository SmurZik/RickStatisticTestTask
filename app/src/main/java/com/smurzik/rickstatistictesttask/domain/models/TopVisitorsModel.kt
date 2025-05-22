package com.smurzik.rickstatistictesttask.domain.models

data class TopVisitorsModel(
    val username: String,
    val isOnline: Boolean,
    val age: Int,
    val imageUri: String = "https://img.freepik.com/free-photo/smiley-man-relaxing-outdoors_23-2148739334.jpg"
)
