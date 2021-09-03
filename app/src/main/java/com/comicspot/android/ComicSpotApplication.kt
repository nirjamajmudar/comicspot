package com.comicspot.android

import android.app.Application
import com.comicspot.android.dagger.AppComponent
import com.comicspot.android.dagger.AppModule
import com.comicspot.android.dagger.DaggerAppComponent
import com.comicspot.android.database.AuthorizationInfoDatabase
import com.comicspot.android.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * The application class - an entry point into our app where we initialize Dagger.
 */

class ComicSpotApplication : Application() {

    companion object {
        private lateinit var appComponent: AppComponent

        fun getAppComponent(): AppComponent {
            return appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        initDaggerAppComponent()
    }

    private fun initDaggerAppComponent(): AppComponent {
        appComponent =
            DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        return appComponent
    }
}