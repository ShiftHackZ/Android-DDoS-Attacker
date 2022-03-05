package com.shifthackz.android.attacker.presentation.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shifthackz.android.attacker.databinding.ItemLogEntryBinding
import com.shifthackz.android.attacker.database.entity.LogEntity

class LogAdapter : ListAdapter<LogEntity, LogAdapter.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemLogEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemLogEntryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LogEntity) {
            binding.item = item
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<LogEntity>() {
            override fun areItemsTheSame(oldItem: LogEntity, newItem: LogEntity): Boolean =
                oldItem.timestamp == newItem.timestamp

            override fun areContentsTheSame(oldItem: LogEntity, newItem: LogEntity): Boolean =
                oldItem == newItem
        }
    }
}