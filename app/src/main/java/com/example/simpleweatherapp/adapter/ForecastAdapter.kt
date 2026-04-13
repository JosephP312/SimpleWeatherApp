package com.example.simpleweatherapp.adapter

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleweatherapp.databinding.ItemForecastBinding
import com.example.simpleweatherapp.model.ForecastItem
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter : ListAdapter<ForecastItem, ForecastAdapter.ForecastViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ForecastViewHolder(ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ForecastViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ForecastItem) {
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(item.dtTxt)
            binding.tvDay.text = date?.let { SimpleDateFormat("EEE", Locale.US).format(it) } ?: "—"
            binding.tvForecastTemp.text = "${item.main.temp.toInt()}°"
            binding.tvForecastHigh.text = "H:${item.main.tempMax.toInt()}°"
            binding.tvForecastLow.text = "L:${item.main.tempMin.toInt()}°"
            Glide.with(binding.root.context)
                .load("https://openweathermap.org/img/wn/${item.weather.firstOrNull()?.icon}@2x.png")
                .into(binding.ivForecastIcon)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ForecastItem>() {
        override fun areItemsTheSame(old: ForecastItem, new: ForecastItem) = old.dt == new.dt
        override fun areContentsTheSame(old: ForecastItem, new: ForecastItem) = old == new
    }
}
