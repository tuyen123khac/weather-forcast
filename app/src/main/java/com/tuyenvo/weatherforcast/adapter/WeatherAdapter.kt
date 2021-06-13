package com.tuyenvo.weatherforcast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuyenvo.weatherforcast.R
import com.tuyenvo.weatherforcast.model.DateInfo

private const val TAG = "WeatherAdapter"

class WeatherAdapter(private val dateInfoList: List<DateInfo>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.weather_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(dateInfoList[position])
    }

    override fun getItemCount(): Int {
        return dateInfoList.size
    }

    inner class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val date_time = view.findViewById<TextView>(R.id.date_time)
        private val average_temperature = view.findViewById<TextView>(R.id.average_temperature)
        private val pressure = view.findViewById<TextView>(R.id.pressure)
        private val humidity = view.findViewById<TextView>(R.id.humidity)
        private val description = view.findViewById<TextView>(R.id.description)

        fun bind(dateInfo: DateInfo) {

            val sdf = java.text.SimpleDateFormat("HH:mm MMM dd, yyyy")
            val date = java.util.Date(dateInfo.dt * 1000)
            sdf.format(date)
            val tempDate = date.toString()
            date_time.text = tempDate.substring(0, 3) + ", " + tempDate.substring(
                8,
                10
            ) + " " + tempDate.substring(4, 7) + " " + tempDate.substring(30, 34)

            val averageTemp = (dateInfo.temp.max + dateInfo.temp.min) / 2
            val solution: Double = String.format("%.0f", averageTemp).toDouble()
            average_temperature.text = solution.toString() + "°C"

            pressure.text = dateInfo.pressure.toString()
            humidity.text = dateInfo.humidity.toString() + "%"
            description.text = dateInfo.weather.get(0).description
        }
    }
}