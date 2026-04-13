package com.example.simpleweatherapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweatherapp.adapter.CityAdapter
import com.example.simpleweatherapp.databinding.FragmentSearchBinding
import com.example.simpleweatherapp.viewmodel.WeatherViewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var cityAdapter: CityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityAdapter = CityAdapter(
            onCityClick = { city -> viewModel.loadWeather(city.cityName); viewModel.loadForecast(city.cityName) },
            onSetDefault = { city -> viewModel.setDefaultCity(city.id); Toast.makeText(requireContext(), "${city.cityName} set as default", Toast.LENGTH_SHORT).show() }
        )
        binding.rvCities.apply { layoutManager = LinearLayoutManager(requireContext()); adapter = cityAdapter }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) { viewModel.deleteCity(cityAdapter.getCityAt(vh.adapterPosition)) }
        }).attachToRecyclerView(binding.rvCities)
        binding.btnAddCity.setOnClickListener {
            val name = binding.etCitySearch.text.toString().trim()
            if (name.isEmpty()) { binding.etCitySearch.error = "Please enter a city name"; return@setOnClickListener }
            viewModel.addCity(name); binding.etCitySearch.setText("")
        }
        viewModel.savedCities.observe(viewLifecycleOwner) { cities ->
            cityAdapter.submitList(cities)
            binding.tvEmptyState.visibility = if (cities.isEmpty()) View.VISIBLE else View.GONE
        }
        viewModel.operationStatus.observe(viewLifecycleOwner) { status ->
            status ?: return@observe
            Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
