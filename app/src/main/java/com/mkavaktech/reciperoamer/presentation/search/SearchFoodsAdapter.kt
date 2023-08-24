package com.mkavaktech.reciperoamer.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.databinding.SearchFoodItemBinding

class SearchFoodsAdapter(private val listener: SearchFoodsListener) :
    RecyclerView.Adapter<SearchFoodsAdapter.SearchFoodsViewHolder>() {

    private val foodList = ArrayList<Meal>()

    interface SearchFoodsListener {
        fun onFoodClick(food: Meal)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFood(foodList: ArrayList<Meal>) {
        this.foodList.clear()
        this.foodList.addAll(foodList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFoodsViewHolder {
        val binding: SearchFoodItemBinding =
            SearchFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchFoodsViewHolder(binding)
    }

    override fun getItemCount(): Int = foodList.size

    override fun onBindViewHolder(holder: SearchFoodsViewHolder, position: Int) {
        val item = foodList[position]
        holder.bind(item)
    }

    inner class SearchFoodsViewHolder(private val binding: SearchFoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedFood = foodList[position]
                    listener.onFoodClick(clickedFood)
                }
            }
        }

        fun bind(item: Meal) {
            binding.searchFoodTxt.text = item.strMeal
            Glide.with(binding.root).load(item.strMealThumb).into(binding.searchFoodImage)
        }
    }

}