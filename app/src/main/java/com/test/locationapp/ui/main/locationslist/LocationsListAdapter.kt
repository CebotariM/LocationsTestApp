package com.test.locationapp.ui.main.locationslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.locationapp.databinding.ListitemLocationBinding
import com.test.locationapp.dto.LocationDto
import com.test.locationapp.ui.main.MainViewModel

class LocationsListAdapter(private val viewModel: MainViewModel) : ListAdapter<LocationDto, LocationsListAdapter.LocationViewHolder>(LocationComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LocationViewHolder(ListitemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LocationViewHolder(private val binding: ListitemLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LocationDto) {
            binding.location = item
            Glide.with(binding.root.context).load(item.image).into(binding.locationImage)
            binding.root.setOnClickListener { viewModel.onLocationSelected(item) }
        }
    }
}

object LocationComparator : DiffUtil.ItemCallback<LocationDto>() {
    override fun areItemsTheSame(oldItem: LocationDto, newItem: LocationDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LocationDto, newItem: LocationDto): Boolean {
        return oldItem.lat == newItem.lat && oldItem.lng == newItem.lng
    }
}