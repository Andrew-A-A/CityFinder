package com.andrewsaba.cityfinder.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
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
        class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
            //Get view widgets
            private val cityName: TextView =view.findViewById(R.id.city_name)
            private val longitude: TextView =view.findViewById(R.id.long_val)
            private val latitude: TextView =view.findViewById(R.id.lat_val)

            //Bind data to views
            fun bind(city: City){
                var displayName= city.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase( //Make sure city name is capitalized
                        Locale.getDefault()
                    ) else it.toString()
                }
                displayName+=", ${city.country}" //Concatenate country code
                cityName.text = displayName
                longitude.text=city.coord.lon.toString()
                latitude.text=city.coord.lat.toString()

                view.setOnClickListener{
                    openMaps(city)  //Set item click listener to open maps using city coordinates
                }
            }

            private fun openMaps(city: City) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=${city.coord.lat}%2C${city.coord.lon}")
                )
                startActivity(this.view.context, intent, null)
            }
        }
    }
}