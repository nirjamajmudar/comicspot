package com.comicspot.android.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.comicspot.android.database.AuthorizationInfo
import com.comicspot.android.database.AuthorizationInfoDao

class AuthRepository(private val authInfoDao: AuthorizationInfoDao) {

    val authInfo: LiveData<AuthorizationInfo> = authInfoDao.getAuthInfo()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(authInfo: AuthorizationInfo) {
        authInfoDao.update(authInfo)
    }
}