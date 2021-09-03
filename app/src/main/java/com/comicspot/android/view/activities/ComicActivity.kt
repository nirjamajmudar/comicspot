package com.comicspot.android.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.comicspot.android.ComicSpotApplication
import com.comicspot.android.Constants
import com.comicspot.android.R
import com.comicspot.android.database.AuthorizationInfo
import com.comicspot.android.utils.AuthUtils
import com.comicspot.android.view.fragments.ComicListFragment
import com.comicspot.android.viewmodel.AuthViewModel
import com.comicspot.android.viewmodel.ViewModelFactory
import javax.inject.Inject


class ComicActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<AuthViewModel>

    private val viewModel: AuthViewModel by lazy {
        viewModelFactory.get<AuthViewModel>(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ComicSpotApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_main)

        if (savedInstanceState == null) {
            val dialog = AuthUtils.buildDialogForAuthorization(this)
            viewModel.authInfo.observe(this, { authInfo ->
                if ((authInfo == null || authInfo.publicKey == Constants.DUMMY_VALUE) && !dialog.isShowing) {
                    dialog.show()
                } else {
                    viewModel.authInfoValue = authInfo
                    if (authInfo.publicKey != Constants.DUMMY_VALUE &&
                            supportFragmentManager.backStackEntryCount == 0) {
                        switchToFragment()
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }


    fun onAuthSuccess(authInfo: AuthorizationInfo) {
        viewModel.update(authInfo)
        switchToFragment()
    }

    private fun switchToFragment() {
        val comicListFragment = ComicListFragment()
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.container, comicListFragment,
                ComicListFragment.TAG
            )
            .commit()
    }


}