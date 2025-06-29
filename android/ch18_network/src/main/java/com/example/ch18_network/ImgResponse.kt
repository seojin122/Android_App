package com.example.ch18_network

data class ItemModel (
    var id: Long = 0,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null
)

data class ImgResponse (
    var articles: MutableList<ItemModel>? = null
)
