package com.example.simpleweatherapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.simpleweatherapp.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserInfo()
        binding.switchTempUnit.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), if (isChecked) "Switched to Celsius (°C)" else "Switched to Fahrenheit (°F)", Toast.LENGTH_SHORT).show()
        }
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Notifications ${if (isChecked) "enabled" else "disabled"}", Toast.LENGTH_SHORT).show()
        }
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "${if (isChecked) "Dark" else "Light"} mode selected", Toast.LENGTH_SHORT).show()
        }
        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
            loadUserInfo()
        }
        binding.tvVersion.text = "Version 1.0.0"
    }

    private fun loadUserInfo() {
        val user = auth.currentUser
        binding.tvUserName.text = user?.displayName ?: "Weather User"
        binding.tvUserEmail.text = user?.email ?: "Not signed in"
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
