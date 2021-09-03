package com.comicspot.android.dagger

import android.app.Application
import com.comicspot.android.ComicSpotApplication
import com.comicspot.android.database.AuthorizationInfoDatabase
import com.comicspot.android.repository.AuthRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
class AppModule constructor(private val application: ComicSpotApplication) {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AuthorizationInfoDatabase.getDatabase(application, applicationScope) }
    val repository by lazy { AuthRepository(database.authInfoDao()) }

    @Provides
    @Singleton
    fun getApplication(): Application {
        return application
    }

    @Provides
    fun getAuthRepository(): AuthRepository {
        return repository
    }
}