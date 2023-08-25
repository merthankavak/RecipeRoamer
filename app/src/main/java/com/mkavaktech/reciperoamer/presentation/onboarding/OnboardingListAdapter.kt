package com.mkavaktech.reciperoamer.presentation.onboarding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkavaktech.reciperoamer.data.entities.OnboardingItem

import com.mkavaktech.reciperoamer.databinding.OnboardingItemBinding

class OnboardingListAdapter :
    RecyclerView.Adapter<OnboardingListAdapter.OnboardingListViewHolder>() {

    private val onboardingItemList = ArrayList<OnboardingItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(onboardingItemList: ArrayList<OnboardingItem>) {
        this.onboardingItemList.clear()
        this.onboardingItemList.addAll(onboardingItemList)
        notifyDataSetChanged()
    }

    inner class OnboardingListViewHolder(private val binding: OnboardingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OnboardingItem) {
            binding.onBoardTitle.text = item.title
            binding.onBoardDesc.text = item.description
            Glide.with(binding.root).load(item.image).into(binding.onBoardImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingListViewHolder {
        val binding: OnboardingItemBinding =
            OnboardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingListViewHolder(binding)
    }

    override fun getItemCount(): Int = onboardingItemList.size

    override fun onBindViewHolder(holder: OnboardingListViewHolder, position: Int) {
        val item = onboardingItemList[position]
        holder.bind(item)
    }
}