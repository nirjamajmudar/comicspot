package com.comicspot.android.repository

import com.comicspot.android.IComicDataSource
import com.comicspot.android.database.AuthorizationInfo
import com.comicspot.android.network.ComicService
import com.comicspot.android.network.model.ComicResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ComicRepository @Inject constructor(private val service: ComicService) : IComicDataSource {

    override fun getComicList(limit: Int, authInfo: AuthorizationInfo): Observable<ComicResponse> {
        return service.getComicList(limit, authInfo.ts, authInfo.publicKey, authInfo.hashValue)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getComicDetails(
        comicId: Int,
        authInfo: AuthorizationInfo?
    ): Observable<ComicResponse> {
        return service.getComicDetails(
            comicId,
            authInfo?.ts,
            authInfo?.publicKey,
            authInfo?.hashValue
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}