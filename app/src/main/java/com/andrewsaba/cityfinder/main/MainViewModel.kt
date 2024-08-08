package com.andrewsaba.cityfinder.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andrewsaba.cityfinder.trie.Trie
import com.andrewsaba.cityfinder.adapter.CitiesListAdapter
import com.andrewsaba.cityfinder.model.City
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.util.Locale


class MainViewModel(private val application: Application):AndroidViewModel(application) {
    //Check if cities list is loaded
     var isDataLoaded=false
     private var isAdapterInitialized=false

    private lateinit var citiesListAdapter: CitiesListAdapter

    //Search results Live data
    private val _searchResults = MutableLiveData<List<City>>()
    val searchResults: MutableLiveData<List<City>> = _searchResults

    //Trie (prefix tree)
    private val _trie = MutableLiveData<Trie>().apply {
        value = Trie() //Initialize trie instance
    }


    //Return cities list adapter
    fun getCitiesListAdapter(): CitiesListAdapter {
        if (!isAdapterInitialized) {
            var cities = mutableListOf<City>()
          try {
               application.assets.open("cities.json").use { inputStream ->
                    JsonReader(InputStreamReader(inputStream)).use { jsonReader ->
                        jsonReader.beginArray()
                        while (jsonReader.hasNext()) {
                            val city = Gson().fromJson<City>(jsonReader, City::class.java)
                            //To make search not case sensitive
                            city.name= city.name.lowercase(Locale.ROOT)
                            cities.add(city)
                            _trie.value!!.addCity(city) // Add directly to trie
                        }
                        jsonReader.endArray()
                        cities
                    }
                }
            } catch (exception: Exception) {
                throw exception
            } finally {
                isDataLoaded = true
            }
            cities=cities.sortedBy { it.name }.toMutableList()
            citiesListAdapter = CitiesListAdapter(cities)
            isAdapterInitialized=true
        }
        return citiesListAdapter
    }


    //Search in trie for a given prefix  (asynchronously)
    fun searchCitiesByPrefix(prefix: String) {
        viewModelScope.launch { //Launch coroutine
            val results = _trie.value!!.searchCitiesByPrefix(prefix.lowercase(Locale.ROOT))
            _searchResults.postValue(results)
        }
    }
}