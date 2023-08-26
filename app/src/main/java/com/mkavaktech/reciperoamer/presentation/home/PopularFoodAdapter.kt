package com.mkavaktech.reciperoamer.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkavaktech.reciperoamer.data.entities.FoodByCategory
import com.mkavaktech.reciperoamer.databinding.PopularFoodItemBinding

class PopularFoodAdapter(private val listener: PopularFoodListener) :
    RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder>() {

    private val foodList = ArrayList<FoodByCategory>()

    interface PopularFoodListener {
        fun onPopularFoodClick(popularFood: FoodByCategory)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFood(foodList: ArrayList<FoodByCategory>) {
        this.foodList.clear()
        this.foodList.addAll(foodList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFoodViewHolder {
        val binding: PopularFoodItemBinding =
            PopularFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularFoodViewHolder(binding)
    }

    override fun getItemCount(): Int = foodList.size

    override fun onBindViewHolder(holder: PopularFoodViewHolder, position: Int) {
        val item = foodList[position]
        holder.bind(item)
    }

    inner class PopularFoodViewHolder(private val binding: PopularFoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedFood = foodList[position]
                    listener.onPopularFoodClick(clickedFood)
                }
            }
        }

        fun bind(item: FoodByCategory) {
            binding.popularFoodTxt.text = item.strMeal
            Glide.with(binding.root).load(item.strMealThumb).into(binding.popularFoodImage)
        }
    }
}