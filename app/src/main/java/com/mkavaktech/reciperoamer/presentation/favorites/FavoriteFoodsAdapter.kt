package com.mkavaktech.reciperoamer.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.databinding.FavFoodItemBinding



class FavoriteFoodsAdapter(private val listener: FavoriteFoodsListener )
    : RecyclerView.Adapter<FavoriteFoodsAdapter.FavoriteFoodsViewHolder>() {

    private val favFoodsList = ArrayList<Meal>()


    interface FavoriteFoodsListener {
        fun onFavoriteFoodsClick(meal: Meal)
        fun onFavIconClick(adapterPosition: Int)
    }


    fun setFavFood(favFoodsList: ArrayList<Meal>) {
        this.favFoodsList.clear()
        this.favFoodsList.addAll(favFoodsList)
        notifyDataSetChanged()
    }

    fun addItem(position: Int, meal: Meal) {
        favFoodsList.add(position, meal)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        if (position in 0 until favFoodsList.size) {
            favFoodsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun foodList() :  ArrayList<Meal> = favFoodsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFoodsViewHolder {
        val binding: FavFoodItemBinding =
            FavFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteFoodsViewHolder(binding)
    }

    override fun getItemCount(): Int = favFoodsList.size

    override fun onBindViewHolder(holder: FavoriteFoodsViewHolder, position: Int) {
        val item = favFoodsList[position]
        holder.bind(item)
    }

    inner class FavoriteFoodsViewHolder(private val binding: FavFoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                favFoodCard.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val clickedFavFood = favFoodsList[position]
                        listener.onFavoriteFoodsClick(clickedFavFood)
                    }
                }
                favSwipeIcon.setOnClickListener {
                    listener.onFavIconClick(adapterPosition)
                }
            }
        }

        fun bind(item: Meal) {
            binding.favFoodTxt.text = item.strMeal
            Glide.with(binding.root).load(item.strMealThumb).into(binding.favFoodImage)
        }
    }


}