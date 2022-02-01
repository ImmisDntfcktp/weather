package com.example.android.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android.weather.databinding.MainActivityBinding
import com.example.android.weather.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipe.setColorSchemeResources(
            android.R.color.white
        )
    }

    override fun onResume() {
        super.onResume()
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
            CoroutineScope(Dispatchers.Main).launch {
                val weather = viewModel.fetchWeatherData((application as? WeatherApp)?.weatherApi)
                weather?.let {
                    refreshData(weather)
                }
            }
            binding.swipe.isRefreshing = false
        }
    }

    @SuppressLint("SetTextI18n")
    fun refreshData(weather: WeatherJson) {
        binding.address.text = weather.name
        val updateData = SimpleDateFormat(" dd/MM hh:mm a", Locale.ENGLISH).format(
            Date(weather.dt.toLong() * 1000)
        )
        binding.updateTime.text = resources.getString(R.string.updated_at) + updateData
        binding.temp.text =
            weather.main.temp.toInt().toString() + resources.getString(R.string.celsius)
        binding.status.text = weather.weather[0].description
        binding.tempMin.text =
            weather.main.temp_min.toInt().toString() + resources.getString(R.string.celsius)
        binding.tempMax.text =
            weather.main.temp_max.toInt().toString() + resources.getString(R.string.celsius)
        binding.sunrise.text = SimpleDateFormat(
            "hh:mm a", Locale.ENGLISH
        ).format(Date(weather.sys.sunrise.toLong() * 1000))
        binding.sunset.text = SimpleDateFormat(
            "hh:mm a", Locale.ENGLISH
        ).format(Date(weather.sys.sunset.toLong() * 1000))
        binding.wind.text = weather.wind.speed.toString()
        binding.pressure.text = weather.main.pressure.toString()
        binding.humidity.text = weather.main.humidity.toString()
    }
}