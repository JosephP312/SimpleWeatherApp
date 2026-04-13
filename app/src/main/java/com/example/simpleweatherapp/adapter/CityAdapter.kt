package com.example.simpleweatherapp.adapter

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleweatherapp.databinding.ItemCityBinding
import com.example.simpleweatherapp.model.SavedCity

class CityAdapter(
    private val onCityClick: (SavedCity) -> Unit,
    private val onSetDefault: (SavedCity) -> Unit
) : ListAdapter<SavedCity, CityAdapter.CityViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CityViewHolder(ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) = holder.bind(getItem(position))
    fun getCityAt(position: Int): SavedCity = getItem(position)

    inner class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: SavedCity) {
            binding.tvCityName.text = "${city.cityName}, ${city.country}"
            binding.tvCityTemp.text = "${city.tempCelsius.toInt()}°F"
            binding.tvCityCondition.text = city.condition
            binding.tvCityHumidity.text = "Humidity: ${city.humidity}%"
            binding.ivDefaultBadge.visibility = if (city.isDefault) View.VISIBLE else View.GONE
            Glide.with(binding.root.context)
                .load("https://openweathermap.org/img/wn/${city.iconCode}@2x.png")
                .into(binding.ivCityIcon)
            binding.root.setOnClickListener { onCityClick(city) }
            binding.root.setOnLongClickListener { onSetDefault(city); true }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SavedCity>() {
        override fun areItemsTheSame(old: SavedCity, new: SavedCity) = old.id == new.id
        override fun areContentsTheSame(old: SavedCity, new: SavedCity) = old == new
    }
}
