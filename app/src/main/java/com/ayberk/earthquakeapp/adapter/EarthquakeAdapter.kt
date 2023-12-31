package com.ayberk.earthquakeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.earthquakeapp.HomeFragmentDirections
import com.ayberk.earthquakeapp.R
import com.ayberk.earthquakeapp.databinding.EarthquakeItemBinding
import com.ayberk.earthquakeapp.model.Result
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class EarthquakeAdapter: RecyclerView.Adapter<EarthquakeAdapter.Earthquakes>(){

    private var livedata: List<com.ayberk.earthquakeapp.model.Result>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Earthquakes {
        val binding = EarthquakeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Earthquakes(binding)
    }

    inner class Earthquakes(private val binding: EarthquakeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:Result){

            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())
            val dateObject = dateFormat.parse(data.date)

            val dayMonthYearFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

            val formattedDate = dayMonthYearFormatter.format(dateObject)

            binding.txtMag.text = data.mag.toString()
            binding.txtCityName.text = data.location_properties.epiCenter.name
            binding.txtCityCode.text = data.location_properties.epiCenter.cityCode.toString()
            binding.txtDate.text = "Tarih: $formattedDate"
            binding.txtTime.text = "Saat: ${SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(dateObject)}"
            binding.txtTitle.text = data.title

        }
    }

    override fun onBindViewHolder(holder: Earthquakes, position: Int) {
        livedata?.let {
            val card = holder.itemView.findViewById<CardView>(R.id.EarthquakeCard)
            card.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(position)
                holder.itemView.findNavController().navigate(action)
                println("id: $action")
            }
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        return livedata?.size ?:0
    }

    fun setEarthquakeList(liveData : List<com.ayberk.earthquakeapp.model.Result>){
        livedata = liveData
        notifyDataSetChanged()
    }
}