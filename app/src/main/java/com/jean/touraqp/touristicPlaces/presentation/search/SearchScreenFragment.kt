package com.jean.touraqp.touristicPlaces.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentSearchScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.fragment_search_screen) {

    private val searchViewModel: SearchViewModel by viewModels()
    private var fragmentSearchScreenBinding : FragmentSearchScreenBinding? = null
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

    private fun initUI(){
        setSearchListAdapter()
        setTouristicPlacesRecyclerView()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                searchViewModel.searchState.collect(){ state ->
                    searchListAdapter.updateList(state.touristicPlaceList)
                    fragmentSearchScreenBinding?.progressBar?.isVisible = state.isLoading
                }
            }
        }
    }

    private fun setSearchListAdapter(){
        searchListAdapter = SearchListAdapter()
    }

    private fun setTouristicPlacesRecyclerView(){
        fragmentSearchScreenBinding?.rvTouristicPlaces?.apply {
            setHasFixedSize(true)
            adapter = searchListAdapter
            layoutManager = LinearLayoutManager(this@SearchScreenFragment.context)
        }
    }
}