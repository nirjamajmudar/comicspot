package com.comicspot.android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide.*
import com.comicspot.android.ComicSpotApplication
import com.comicspot.android.Constants
import com.comicspot.android.R
import com.comicspot.android.network.model.ComicResults
import com.comicspot.android.utils.ComicUtils
import com.comicspot.android.viewmodel.ComicViewModel
import com.comicspot.android.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * Displays comic title, description and cover image to the user.
 */

class ComicDetailsFragment : Fragment() {

    companion object {
        const val TAG = "ComicDetailsFragment"
    }

    @Inject
    lateinit var comicViewModelFactory: ViewModelFactory<ComicViewModel>

    private val comicViewModel: ComicViewModel by lazy {
        comicViewModelFactory.get<ComicViewModel>(
            requireActivity()
        )
    }

    private var comicDetails: ComicResults? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ComicSpotApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        comicDetails = comicViewModel.comicInfo
        val view = inflater.inflate(R.layout.fragment_comic_details, container, false)

        savedInstanceState?.let {
            setUpView(
                view, it.getString(Constants.COMIC_TITLE),
                it.getString(Constants.COMIC_DESC),
                it.getString(Constants.COMIC_IMAGE_PATH),
                it.getString(Constants.COMIC_IMAGE_EXTENSION)
            )
        } ?: run {
            setUpView(
                view,
                comicDetails?.title,
                comicDetails?.description,
                comicDetails?.thumbnail?.path,
                comicDetails?.thumbnail?.extension
            )
        }

        comicViewModel.comicErrorLiveData.observe(viewLifecycleOwner, {
            ComicUtils.buildErrorDialog(context, context?.getString(R.string.generic_error)).show()
        })

        return view
    }

    private fun setUpView(
        view: View?,
        title: String?,
        description: String?,
        path: String?,
        extension: String?
    ) {
        view?.findViewById<AppCompatTextView>(R.id.comic_title)?.text = title
        view?.findViewById<AppCompatTextView>(R.id.comic_description)?.text = description

        view?.let {
            with(this).load(
                ComicUtils.generateImagePath(
                    path, extension, Constants.LANDSCAPE_INCREDIBLE
                )
            ).into(it.findViewById<AppCompatImageView>(R.id.comic_cover_image))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.COMIC_TITLE, comicDetails?.title)
        outState.putString(Constants.COMIC_DESC, comicDetails?.description)
        outState.putString(Constants.COMIC_IMAGE_PATH, comicDetails?.thumbnail?.path)
        outState.putString(Constants.COMIC_IMAGE_EXTENSION, comicDetails?.thumbnail?.extension)
    }
}