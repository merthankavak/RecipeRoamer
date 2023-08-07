package com.mkavaktech.reciperoamer.presentation.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.mkavaktech.reciperoamer.data.entities.FoodByCategory
import com.mkavaktech.reciperoamer.databinding.CategoryItemBinding


class CategoryFoodAdapter(private val listener: CategoryFoodListener) :
    RecyclerView.Adapter<CategoryFoodAdapter.CategoryFoodViewHolder>() {

    private val foodList = ArrayList<FoodByCategory>()


    interface CategoryFoodListener {
        fun onFoodClick(foodByCategory: FoodByCategory)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFoods(foodList: ArrayList<FoodByCategory>) {
        this.foodList.clear()
        this.foodList.addAll(foodList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFoodViewHolder {
        val binding: CategoryItemBinding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryFoodViewHolder(binding)
    }

    override fun getItemCount(): Int = foodList.size

    override fun onBindViewHolder(holder: CategoryFoodViewHolder, position: Int) {
        val item = foodList[position]
        holder.bind(item)
    }

    inner class CategoryFoodViewHolder(private val binding: CategoryItemBinding) :
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

        fun bind(item: FoodByCategory) {
            binding.categoryItemTxt.text = item.strMeal
            Glide.with(binding.root).load(item.strMealThumb).into(binding.categoryItemImage)
        }
    }

}