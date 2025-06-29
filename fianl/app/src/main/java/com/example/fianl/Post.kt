package com.example.fianl

import com.google.firebase.firestore.Exclude

data class Post(
    @get:Exclude var id: String = "" ,
    val title: String = "",
    val content: String = "",
    var likes: Int = 0,
    var timestamp: Long = 0L
)
