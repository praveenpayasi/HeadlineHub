package com.praveenpayasi.headlinehub.data.model.topheadlines

import com.google.gson.annotations.SerializedName
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity

data class ApiTopHeadlines(
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String = "",
    @SerializedName("urlToImage") val imageUrl: String = "",
    @SerializedName("source") val apiSource: ApiSource
)

fun ApiTopHeadlines.toTopHeadlineEntity(country: String) = TopHeadlineEntity(
    title = title,
    description = description ?: "",
    url = url,
    imageUrl = imageUrl ?: "",
    country = country,
    source = apiSource.toSourceEntity()
)