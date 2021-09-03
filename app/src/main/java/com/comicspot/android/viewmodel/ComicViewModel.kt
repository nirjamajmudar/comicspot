package com.comicspot.android.viewmodel

import androidx.lifecycle.*
import com.comicspot.android.Constants
import com.comicspot.android.database.AuthorizationInfo
import com.comicspot.android.network.model.ComicResponse
import com.comicspot.android.network.model.ComicResults
import com.comicspot.android.repository.ComicRepository
import io.reactivex.observers.ResourceObserver
import retrofit2.HttpException
import javax.inject.Inject

class ComicViewModel @Inject constructor(
    private val repository: ComicRepository) : ViewModel() {

    var comicListLiveData = MutableLiveData<List<ComicResults>>()
    var comicErrorLiveData = MutableLiveData<String>()

    var comicDetailsLiveData = MutableLiveData<ComicResults>()
    var comicList = ArrayList<ComicResults>()
    var comicInfo : ComicResults? = null

    fun getComicList(limit: Int, authInfo: AuthorizationInfo) {
        repository.getComicList(limit, authInfo)
            .subscribe(object : ResourceObserver<ComicResponse>() {
                override fun onNext(response: ComicResponse) {
                    response.data?.results?.let {
                        comicList = it as ArrayList<ComicResults>
                        comicListLiveData.postValue(it)
                    }
                }

                override fun onError(e: Throwable) {
                    comicErrorLiveData.postValue(e.message)
                    dispose()
                }

                override fun onComplete() {
                    dispose()
                }

            })
    }

    fun getComicDetails(comicId: Int, authInfo: AuthorizationInfo?) {
        repository.getComicDetails(comicId, authInfo)
            .subscribe(object : ResourceObserver<ComicResponse>() {
                override fun onNext(response: ComicResponse) {
                    response.data?.results?.let {
                        comicInfo = it[0]
                        comicDetailsLiveData.postValue(it[0])
                    }
                }

                override fun onError(e: Throwable) {
                    if ((e as HttpException).code() == Constants.PAGE_NOT_FOUND) {
                        comicErrorLiveData.postValue((Constants.PAGE_NOT_FOUND).toString())
                    } else {
                        comicErrorLiveData.postValue(e.message)
                    }
                    dispose()
                }

                override fun onComplete() {
                    dispose()
                }
            })
    }
}