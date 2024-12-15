package com.jean.touraqp.touristicPlaces.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jean.touraqp.R
import com.jean.touraqp.core.auth.AuthViewModel
import com.jean.touraqp.databinding.FragmentSearchScreenBinding
import com.jean.touraqp.touristicPlaces.presentation.shared.SharedViewModel
import com.jean.touraqp.touristicPlaces.presentation.shared.TouristicPlaceEffect
import com.jean.touraqp.touristicPlaces.presentation.shared.TouristicPlaceEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.fragment_search_screen) {

    private val authViewModel: AuthViewModel by activityViewModels()
    private val sharedVieModel: SharedViewModel by hiltNavGraphViewModels(R.id.core_graph)

    private var binding: FragmentSearchScreenBinding? = null
    private lateinit var searchListAdapter: SearchListAdapter


    companion object {
        const val TAG = "search_screen"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchScreenBinding.bind(view)
        initUI()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initUI() {
        setSearchListAdapter()
        setTouristicPlacesRecyclerView()
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        binding?.apply {
            searchView.editText.doOnTextChanged { text, start, before, count ->
                sharedVieModel.onEvent(TouristicPlaceEvent.OnSearchInputChanged(text.toString()))
            }

            searchView.editText.setOnEditorActionListener() { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sharedVieModel.onEvent(TouristicPlaceEvent.OnSearchAction)
                    searchView.hide()
                    searchBar.setText(sharedVieModel.state.value.searchQuery)
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    sharedVieModel.effect.collect() {
                        when (it) {
                            TouristicPlaceEffect.OnSelectTouristicPlace -> {
                                navigateToDetailTouristicPlace()
                            }
                        }
                    }
                }

                launch {
                    sharedVieModel.state.collect() { state ->
                        binding?.apply {
                            progressBar.isVisible = state.isLoading
                        }
                        searchListAdapter.updateList(state.filteredTouristicPlaces)
                    }
                }
                launch {
                    authViewModel.state.collect() { state ->
                        binding?.apply {
                            topAppBar.title = "Hello ${state.name}"
                            topAppBar.subtitle = "@${state.username}"
                        }
                    }
                }
            }
        }
    }


    private fun navigateToDetailTouristicPlace() {
        findNavController().navigate(
            SearchScreenFragmentDirections.actionSearchScreenFragmentToTouristicPlaceDetailScreenFragment()
        )
    }

    private fun setSearchListAdapter() {
        searchListAdapter = SearchListAdapter(onClickListener = { id ->
            sharedVieModel.onEvent(TouristicPlaceEvent.OnSelectTouristicPlace(id))
        })
    }

    private fun setTouristicPlacesRecyclerView() {
        binding?.rvTouristicPlaces?.apply {
            setHasFixedSize(true)
            adapter = searchListAdapter
            layoutManager = LinearLayoutManager(this@SearchScreenFragment.context)
        }
    }
}