package com.ents_h108.petwell.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ArticleResponse(
    val listArticle: List<Article>,
    val error: Boolean,
    val message: String
)

@Keep
data class Article(
    @SerializedName("_id") val id: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val type: String
)