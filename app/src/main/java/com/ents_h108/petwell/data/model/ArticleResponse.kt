package com.ents_h108.petwell.data.model

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

@field:SerializedName("listArticle")
val listArticle: List<Article>,

@field:SerializedName("error")
val error: Boolean,

@field:SerializedName("message")
val message: String
)
data class Article(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("desc") val description: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("type") val type: String
)