package com.jean.touraqp.touristicPlaces.presentation.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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

    private var fragmentSearchScreenBinding: FragmentSearchScreenBinding? = null
    private lateinit var searchListAdapter: SearchListAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSearchScreenBinding = FragmentSearchScreenBinding.bind(view)
        initUI()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentSearchScreenBinding = null
    }

    private fun initUI() {
        setSearchListAdapter()
        setTouristicPlacesRecyclerView()
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
                        fragmentSearchScreenBinding?.apply {
                            progressBar.isVisible = state.isLoading
                        }
                        searchListAdapter.updateList(state.touristicPlaces)
                    }
                }
                launch {
                    authViewModel.state.collect(){ state ->
                        fragmentSearchScreenBinding?.apply {
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
        fragmentSearchScreenBinding?.rvTouristicPlaces?.apply {
            setHasFixedSize(true)
            adapter = searchListAdapter
            layoutManager = LinearLayoutManager(this@SearchScreenFragment.context)
        }
    }
}