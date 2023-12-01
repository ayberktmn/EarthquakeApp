package com.ayberk.earthquakeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.earthquakeapp.databinding.EarthquakeItemBinding
import com.ayberk.earthquakeapp.model.Result


class EarthquakeAdapter: RecyclerView.Adapter<EarthquakeAdapter.Earthquakes>(){

    private var livedata: List<com.ayberk.earthquakeapp.model.Result>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Earthquakes {
        val binding = EarthquakeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Earthquakes(binding)
    }

    inner class Earthquakes(private val binding: EarthquakeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:Result){

        }
    }

    override fun onBindViewHolder(holder: Earthquakes, position: Int) {

    }

    override fun getItemCount(): Int {
        return livedata?.size ?:0
    }

    fun setEarthquakeList(liveData : List<com.ayberk.earthquakeapp.model.Result>){
        livedata = liveData
        notifyDataSetChanged()
    }
}