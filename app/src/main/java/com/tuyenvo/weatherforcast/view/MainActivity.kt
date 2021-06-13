package com.tuyenvo.weatherforcast.view

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.tuyenvo.weatherforcast.R
import com.tuyenvo.weatherforcast.adapter.WeatherAdapter
import com.tuyenvo.weatherforcast.databinding.ActivityMainBinding
import com.tuyenvo.weatherforcast.viewmodel.MainViewModel

private const val TAG = "MainActivity"
private const val KEY_CITY_NAME = "cityName"
private const val DEFAULT_CITY_VALUE = "Sai Gon"

class MainActivity : AppCompatActivity() {

    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var binding: ActivityMainBinding
    private lateinit var defaultCityName: String
    private lateinit var defaultCityNameQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitializing()
        getLiveData()
        setListener()
    }

    /**
     * Initializing mandatory setup
     */
    private fun doInitializing(){
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()

        defaultCityName =
            sharedPreferences.getString(KEY_CITY_NAME, DEFAULT_CITY_VALUE)!!
        defaultCityNameQuery = defaultCityName.trim().lowercase().replace("\\s".toRegex(), "")

        binding.editQuery.setText(defaultCityName)
        viewModel.refreshData(defaultCityNameQuery)
    }

    /**
     * Implement functionalities for "Get weather" button and swipe to refresh data
     */
    private fun setListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            val cityName = sharedPreferences.getString(KEY_CITY_NAME, defaultCityName).toString()
            val cityNameQuery = cityName.trim().lowercase().replace("\\s".toRegex(), "")
            Log.d(TAG, "Swipe to refresh, current query is: " + cityNameQuery)
            binding.editQuery.setText(cityName)
            viewModel.refreshData(cityNameQuery)
            binding.editQuery.clearFocus()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.getWeatherButton.setOnClickListener {
            val cityName = binding.editQuery.text.toString()
            val cityNameQuery = cityName.trim().lowercase().replace("\\s".toRegex(), "")
            if (cityNameQuery.length < 3) {
                binding.linearCity.visibility = View.GONE
                binding.linearCountry.visibility = View.GONE
                binding.weatherList.visibility = View.GONE
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = getString(R.string.invalid_search_term_length_error)
                Log.d(TAG, "onGetWeatherButtonClick: Search term length is less than 3 characters")
            } else {
                Log.d(TAG, "Querying: " + cityNameQuery)
                binding.weatherList.visibility = View.VISIBLE
                binding.errorText.visibility = View.GONE
                sharedPreferencesEditor.putString(KEY_CITY_NAME, cityName)
                sharedPreferencesEditor.apply()
                viewModel.refreshData(cityNameQuery)
                binding.editQuery.clearFocus()
            }
        }
    }

    /**
     * Get Live data from ViewModel and reflect to UI
     */
    private fun getLiveData() {
        viewModel.weatherData.observe(this, { data ->
            data?.let {
                binding.cityName.text = data.city.name
                binding.countryName.text = data.city.country
                weatherAdapter = WeatherAdapter(
                    data.list
                )
                binding.weatherList.adapter = weatherAdapter
                weatherAdapter.notifyDataSetChanged()
                binding.linearCity.visibility = View.VISIBLE
                binding.linearCountry.visibility = View.VISIBLE
                binding.weatherList.visibility = View.VISIBLE
                binding.errorText.visibility = View.GONE
                Log.d(TAG, "getLiveData: Fetched Live data successfully")
            }
        })

        viewModel.weatherError.observe(this, { error ->
            error?.let {
                if (error) {
                    Log.d(TAG, "getLiveData: Error in fetching Live data")
                    binding.linearCity.visibility = View.GONE
                    binding.linearCountry.visibility = View.GONE
                    binding.weatherList.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = getString(R.string.network_error_or_wrong_query)
                }
            }
        })

        viewModel.weatherLoading.observe(this, { loading ->
            loading?.let {
                if (loading) {
                    Log.d(TAG, "getLiveData: Start fetching Live data")
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    Log.d(TAG, "getLiveData: Stop fetching Live data")
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }
}