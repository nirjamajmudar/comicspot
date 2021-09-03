package com.comicspot.android

import com.comicspot.android.database.AuthorizationInfo
import com.comicspot.android.network.model.ComicResponse
import io.reactivex.Observable

interface IComicDataSource {

    fun getComicList(limit: Int, authInfo: AuthorizationInfo): Observable<ComicResponse>

    fun getComicDetails(comicId: Int, authInfo: AuthorizationInfo?): Observable<ComicResponse>
}