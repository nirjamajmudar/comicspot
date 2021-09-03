package com.comicspot.android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.comicspot.android.ComicSpotApplication
import com.comicspot.android.Constants
import com.comicspot.android.R
import com.comicspot.android.network.model.ComicResults
import com.comicspot.android.utils.ComicUtils
import com.comicspot.android.view.adapters.ComicListAdapter
import com.comicspot.android.view.adapters.ComicView
import com.comicspot.android.viewmodel.AuthViewModel
import com.comicspot.android.viewmodel.ComicViewModel
import com.comicspot.android.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * Displays the list of Comics with its title and the thumbnail to the user.
 */

class ComicListFragment : Fragment(), ComicView.OnComicItemClickListener {

    companion object {
        const val TAG = "ComicListFragment"
    }

    private lateinit var comicListAdapter: ComicListAdapter
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var comicViewModelFactory: ViewModelFactory<ComicViewModel>

    private val comicViewModel: ComicViewModel by lazy {
        comicViewModelFactory.get<ComicViewModel>(
            requireActivity()
        )
    }

    @Inject
    lateinit var authViewModelFactory: ViewModelFactory<AuthViewModel>

    private val authViewModel: AuthViewModel by lazy {
        authViewModelFactory.get<AuthViewModel>(
            requireActivity()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ComicSpotApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comic_list, container, false)

        comicListAdapter = ComicListAdapter(ArrayList(), this)
        recyclerView = view.findViewById(R.id.comics_view)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = comicListAdapter
        }

        if (comicViewModel.comicList.isEmpty() && authViewModel.authInfoValue != null) {
            setUpObservers()
            authViewModel.authInfoValue?.let {
                comicViewModel.getComicList(
                    Constants.COMIC_LIST_LIMIT,
                    it
                )
            }
        } else {
            comicListAdapter.updateData(comicViewModel.comicList as MutableList<ComicResults>)
        }
        return view
    }

    private fun setUpObservers() {
        comicViewModel.comicListLiveData.observe(this.viewLifecycleOwner, { list ->
            comicListAdapter.updateData(list as MutableList<ComicResults>)
        })


        comicViewModel.comicErrorLiveData.observe(this.viewLifecycleOwner, { errorMessage ->
            val errorText: String? = if (errorMessage == (Constants.PAGE_NOT_FOUND).toString()) {
                context?.getString(R.string.page_not_found_error)
            } else {
                context?.getString(R.string.generic_error)
            }
            ComicUtils.buildErrorDialog(context, errorText).show()
        })
    }

    override fun itemClicked(comicId: Int) {
        comicViewModel.comicDetailsLiveData.observe(viewLifecycleOwner, { result ->
            if (comicId == result.id) {
                val comicDetailsFragment = ComicDetailsFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.container, comicDetailsFragment,
                    ComicDetailsFragment.TAG
                )?.addToBackStack(null)?.commit()
            }
        })

        comicViewModel.getComicDetails(comicId, authViewModel.authInfoValue)
    }
}