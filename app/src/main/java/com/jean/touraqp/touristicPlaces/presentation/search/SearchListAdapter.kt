package com.jean.touraqp.touristicPlaces.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jean.touraqp.R
import com.jean.touraqp.databinding.ItemTouristicPlaceBinding
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

class SearchListAdapter(
    private var touristicPlaceList: List<TouristicPlace> = emptyList()
): RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_touristic_place, parent, false)
        return SearchListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return touristicPlaceList.size
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.bind(touristicPlaceList[position])
    }

    fun updateList(list : List<TouristicPlace>){
        this.touristicPlaceList = list
        notifyDataSetChanged()
    }


    inner class SearchListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val binding = ItemTouristicPlaceBinding.bind(itemView)

        fun bind(touristicPlace: TouristicPlace){
            binding.apply {
                titlePlace.text = touristicPlace.name
                descriptionPlace.text = touristicPlace.description
            }
        }
    }

}