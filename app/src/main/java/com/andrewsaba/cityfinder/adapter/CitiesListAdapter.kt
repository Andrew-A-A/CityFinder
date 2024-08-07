package com.andrewsaba.cityfinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andrewsaba.cityfinder.R
import com.andrewsaba.cityfinder.model.City

class CitiesListAdapter(private val cities: MutableList<City>):
RecyclerView.Adapter<CitiesListAdapter.ViewHolder>() {

    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val cityName: TextView =view.findViewById(R.id.city_name)
        private val longitude: TextView =view.findViewById(R.id.long_val)
        private val latitude: TextView =view.findViewById(R.id.lat_val)
        fun bind(city: City){
            cityName.text=city.name
            longitude.text=city.coord.lon.toString()
            latitude.text=city.coord.lat.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.city_item,parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }
}