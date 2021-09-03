package com.comicspot.android.network.model

import com.google.gson.annotations.SerializedName

data class ComicResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: ResponseData?
)

data class ResponseData(
    @SerializedName("results")
    val results: List<ComicResults>?
)

data class ComicResults(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailInfo
)

data class ThumbnailInfo(
    @SerializedName("path")
    val path: String?,
    @SerializedName("extension")
    val extension: String?
)
