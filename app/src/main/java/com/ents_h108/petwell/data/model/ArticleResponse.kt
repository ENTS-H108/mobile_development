package com.ents_h108.petwell.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class ArticleResponse(
    val listArticle: List<Article>,
    val error: Boolean,
    val message: String
)

@Keep
@Parcelize
data class Article(
    @SerializedName("_id") val id: String,
    val title: String,
    val desc: String,
    val thumbnail: String,
    val type: String,
    val timestamp: String
) : Parcelable