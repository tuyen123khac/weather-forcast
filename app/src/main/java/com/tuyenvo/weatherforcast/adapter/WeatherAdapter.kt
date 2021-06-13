package com.tuyenvo.weatherforcast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuyenvo.weatherforcast.databinding.WeatherItemContainerBinding
import com.tuyenvo.weatherforcast.model.DateInfo

private const val TAG = "WeatherAdapter"

class WeatherAdapter(private val dateInfoList: List<DateInfo>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WeatherItemContainerBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(binding)

    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(dateInfoList[position])
    }

    override fun getItemCount(): Int {
        return dateInfoList.size
    }

    inner class WeatherViewHolder(val binding: WeatherItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dateInfo: DateInfo) {
            val sdf = java.text.SimpleDateFormat("dd-MM-yyyy")
            val date = java.util.Date(dateInfo.dt * 1000)
            sdf.format(date)
            val tempDate = date.toString()
            binding.dateTime.text = tempDate.substring(0, 3) + ", " + tempDate.substring(
                8,
                10
            ) + " " + tempDate.substring(4, 7) + " " + tempDate.substring(30, 34)

            val averageTemp = (dateInfo.temp.max + dateInfo.temp.min) / 2
            val solution: Double = String.format("%.0f", averageTemp).toDouble()
            binding.averageTemperature.text = solution.toInt().toString() + "°C"

            binding.pressure.text = dateInfo.pressure.toString()
            binding.humidity.text = dateInfo.humidity.toString() + "%"
            binding.description.text = dateInfo.weather.get(0).description
        }
    }
}