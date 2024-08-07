package com.andrewsaba.cityfinder.main

import android.app.Application
import android.util.JsonReader
import androidx.lifecycle.AndroidViewModel
import com.andrewsaba.cityfinder.adapter.CitiesListAdapter
import com.andrewsaba.cityfinder.model.City
import com.google.gson.Gson


class MainViewModel(application: Application):AndroidViewModel(application) {
    //Check if cities list is loaded
    val isLoaded=false
    //Load cities list from JSON file
    private val _cities: MutableList<City> =try {
        val jsonString= application.assets.open("cities.json").bufferedReader().use { it.readText() }
        Gson().fromJson(jsonString,Array<City>::class.java).toList().toMutableList()
    } catch (exception: Exception){
        throw exception
    }
    //Return cities list
    fun getCities():MutableList<City>{
        return _cities
    }

    //Return cities list adapter
    fun getCitiesListAdapter(): CitiesListAdapter {
        return CitiesListAdapter(_cities)
    }

}