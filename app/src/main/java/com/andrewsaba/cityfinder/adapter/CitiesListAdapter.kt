package com.andrewsaba.cityfinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andrewsaba.cityfinder.R
import com.andrewsaba.cityfinder.model.City
import java.util.Locale

class CitiesListAdapter(private val cities: List<City>):
RecyclerView.Adapter<CitiesListAdapter.Companion.ViewHolder>() {

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

    companion object {
        class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            private val cityName: TextView =view.findViewById(R.id.city_name)
            private val longitude: TextView =view.findViewById(R.id.long_val)
            private val latitude: TextView =view.findViewById(R.id.lat_val)

            //Bind data to views
            fun bind(city: City){
                var displayName= city.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                displayName+=", ${city.country}" //Concat country code
                cityName.text = displayName
                longitude.text=city.coord.lon.toString()
                latitude.text=city.coord.lat.toString()
            }
        }
    }
}