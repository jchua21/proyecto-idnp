package com.jean.touraqp.touristicPlaces.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.ImageLoader
import coil3.load
import coil3.size.Size
import com.jean.touraqp.R
import com.jean.touraqp.databinding.ItemTouristicPlaceBinding
import com.jean.touraqp.touristicPlaces.presentation.model.TouristicPlaceWithReviewsUI
import javax.inject.Inject

class SearchListAdapter(
    private var touristicPlaceList: List<TouristicPlaceWithReviewsUI> = emptyList(),
    private val onClickListener: (id: String) -> Unit
): RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>(){

    @Inject lateinit var imageLoader: ImageLoader

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

    fun updateList(list : List<TouristicPlaceWithReviewsUI>){
        this.touristicPlaceList = list
        notifyDataSetChanged()
    }


    inner class SearchListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val binding = ItemTouristicPlaceBinding.bind(itemView)

        fun bind(touristicPlace: TouristicPlaceWithReviewsUI){
            binding.apply {
                root.setOnClickListener(){onClickListener(touristicPlace.id)}
                titlePlace.text = touristicPlace.name
                descriptionPlace.text = touristicPlace.description
                imagePlace.load(touristicPlace.imageUrl){
                    size(Size.ORIGINAL)
                }

            }
        }
    }

}