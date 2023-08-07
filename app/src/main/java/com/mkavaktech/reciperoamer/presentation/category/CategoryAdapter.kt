package com.mkavaktech.reciperoamer.presentation.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkavaktech.reciperoamer.data.entities.Category
import com.mkavaktech.reciperoamer.databinding.FoodCategoriesBinding


class CategoryAdapter(private val listener: CategoryListener) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val categoryList = ArrayList<Category>()


    interface CategoryListener {
        fun onCategoryClick(category: Category)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCategory(categoryList: ArrayList<Category>) {
        this.categoryList.clear()
        this.categoryList.addAll(categoryList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: FoodCategoriesBinding =
            FoodCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categoryList[position]
        holder.bind(item)
    }

    inner class CategoryViewHolder(private val binding: FoodCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedCategory = categoryList[position]
                    listener.onCategoryClick(clickedCategory)
                }
            }
        }

        fun bind(item: Category) {
            binding.foodCategoryTxt.text = item.strCategory
            Glide.with(binding.root).load(item.strCategoryThumb).into(binding.foodCategoryImage)
        }
    }

}