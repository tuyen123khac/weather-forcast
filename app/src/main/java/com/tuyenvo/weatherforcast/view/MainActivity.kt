package com.tuyenvo.weatherforcast.view

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.tuyenvo.weatherforcast.R
import com.tuyenvo.weatherforcast.adapter.WeatherAdapter
import com.tuyenvo.weatherforcast.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val KEY_CITY_NAME = "cityName"
private const val DEFAULT_CITY_VALUE = "saigon"

class MainActivity : AppCompatActivity() {

    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var defaultCityName = sharedPreferences.getString(KEY_CITY_NAME, DEFAULT_CITY_VALUE)?.lowercase()
        edit_query.setText(defaultCityName)
        viewModel.refreshData(defaultCityName!!)

        getLiveData()

        swipe_refresh_layout.setOnRefreshListener {
            var cityName = sharedPreferences.getString(KEY_CITY_NAME, defaultCityName)?.lowercase()
            edit_query.setText(cityName)
            viewModel.refreshData(cityName!!)
            getLiveData()
            edit_query.clearFocus()
            swipe_refresh_layout.isRefreshing = false
        }

        get_weather_button.setOnClickListener {
            val cityName = edit_query.text.toString()
            sharedPreferencesEditor.putString(KEY_CITY_NAME, cityName)
            sharedPreferencesEditor.apply()
            viewModel.refreshData(cityName)
            getLiveData()
            edit_query.clearFocus()
            Log.d(TAG, "onCreate: " + cityName)
        }

    }

    private fun getLiveData() {

        viewModel.weatherData.observe(this, { data ->
            data?.let {
                weatherAdapter = WeatherAdapter(
                    data.list
                )
                weather_list.adapter = weatherAdapter
                weatherAdapter.notifyDataSetChanged()
                progress_bar.visibility = View.INVISIBLE
                Log.d(TAG, "getLiveData: observe success")
            }
        })

        viewModel.weatherError.observe(this, { error ->
            error?.let {
                Log.d(TAG, "getLiveData: observe error")
                //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.weatherLoading.observe(this, { loading ->
            loading?.let {
                Log.d(TAG, "getLiveData: observe is loading")
                //progress_bar.visibility = View.VISIBLE
            }
        })
    }
}