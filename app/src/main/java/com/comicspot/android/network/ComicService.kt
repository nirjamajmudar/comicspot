package com.comicspot.android.network

import com.comicspot.android.Constants
import com.comicspot.android.network.model.ComicResponse
import com.comicspot.android.network.model.ComicResults
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Communicates with the Marvel backend to obtain data.
 */

interface ComicService {
    /**
     * Returns the list of comics per size specified as limit
     */
    @GET("v1/public/comics")
    fun getComicList(
        @Query("limit") limit: Int,
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Observable<ComicResponse>

    /**
     * Returns the details of the comic per specified comicId
     */

    @GET("v1/public/comics/{comicId}")
    fun getComicDetails(
        @Path("comicId") comicId: Int,
        @Query("ts") ts: Long?,
        @Query("apikey") apikey: String?,
        @Query("hash") hash: String?
    ): Observable<ComicResponse>
}