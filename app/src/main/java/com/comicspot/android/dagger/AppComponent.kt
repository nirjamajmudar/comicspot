package com.comicspot.android.dagger

import com.comicspot.android.ComicSpotApplication
import com.comicspot.android.repository.AuthRepository
import com.comicspot.android.view.activities.ComicActivity
import com.comicspot.android.view.fragments.ComicDetailsFragment
import com.comicspot.android.view.fragments.ComicListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun appModule(module: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: ComicSpotApplication)
    fun inject(comicActivity: ComicActivity)
    fun inject(comicListFragment: ComicListFragment)
    fun inject(comicDetailsFragment: ComicDetailsFragment)
    fun inject(repository: AuthRepository)

}