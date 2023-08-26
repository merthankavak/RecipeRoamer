package com.mkavaktech.reciperoamer.presentation.info

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkavaktech.reciperoamer.data.entities.InfoItem
import com.mkavaktech.reciperoamer.databinding.HeaderItemBinding
import com.mkavaktech.reciperoamer.databinding.InfoItemBinding

class InfoListAdapter(private val listener: InfoListListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }

    private val infoList = ArrayList<InfoItem>()

    interface InfoListListener {
        fun onInfoItemClick(position: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setInfoItem(infoList: ArrayList<InfoItem>) {
        this.infoList.clear()
        this.infoList.addAll(infoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val headerBinding: HeaderItemBinding = HeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                HeaderViewHolder(headerBinding)
            }

            VIEW_TYPE_ITEM -> {
                val infoBinding: InfoItemBinding = InfoItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                InfoListViewHolder(infoBinding)
            }

            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int = infoList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = infoList[position]
        when (holder) {
            is HeaderViewHolder -> holder.bindHeader(item)
            is InfoListViewHolder -> holder.bindInfoItem(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (infoList[position].isHeader) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    inner class HeaderViewHolder(private val binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindHeader(item: InfoItem) {
            binding.headerTitle.text = item.title
        }
    }

    inner class InfoListViewHolder(private val binding: InfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onInfoItemClick(position)
                }
            }
        }

        fun bindInfoItem(item: InfoItem) {
            binding.infoIconBg.setImageResource(item.colorResId)
            binding.infoTitle.text = item.title
            Glide.with(binding.root).load(item.iconResId).into(binding.infoIcon)

        }
    }
}