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

     private var isAdapterInitialized=false

    //Cities recycler view adapter
    private lateinit var citiesListAdapter: CitiesListAdapter

    //Search results Live data
    private val _searchResults = MutableLiveData<List<City>>()
    val searchResults: MutableLiveData<List<City>> = _searchResults

    //Trie (prefix tree) object
    private val _trie =  Trie()

    //Return cities list adapter
    fun getCitiesListAdapter(): CitiesListAdapter {
        if (!isAdapterInitialized) {
            var cities = mutableListOf<City>()
          try {
              loadDataFromJSON(cities)
          }
          catch (exception: Exception) {
              throw exception
            }
            cities=cities.sortedBy { it.name }.toMutableList() //Sort cities by name
            citiesListAdapter = CitiesListAdapter(cities) //Initialize recycler view adapter
            isAdapterInitialized=true //Set adapter initialization flag to true
        }
        return citiesListAdapter
    }

    //Search in trie for a given prefix  (asynchronously)
    fun searchCitiesByPrefix(prefix: String) {
        viewModelScope.launch { //Launch coroutine
            var searchString=prefix.lowercase(Locale.ROOT)

            //Case user entered country code
            if (searchString.contains("\\w+,".toRegex())){  //Check for the comma using regex
                val countryCode=searchString
                    .split(",")[1] //Split country code from search string
                    .uppercase() //Make sure country code is upper case
                    .trim()  //Remove any white spaces

                searchString=searchString.split(",")[0] //Split city name

                var results = _trie.searchCitiesByPrefix(searchString) //Search in trie

                results=results.filter {
                    it.country.contains(countryCode) //Filter search results using country code
                }
                _searchResults.postValue(results) //Set final search result
            }

            //Case user entered city name only
            else{
                val results = _trie.searchCitiesByPrefix(searchString)
                _searchResults.postValue(results)
            }
        }
    }

    private fun loadDataFromJSON(cities: MutableList<City>) {
        application.assets.open("cities.json").use { inputStream ->
            JsonReader(InputStreamReader(inputStream)).use { jsonReader ->
                jsonReader.beginArray()
                initializeCitiesList(jsonReader, cities)
                jsonReader.endArray()
                cities
            }
        }
    }

    private fun initializeCitiesList(jsonReader: JsonReader, cities: MutableList<City>) {
        while (jsonReader.hasNext()) {
            val city = Gson().fromJson<City>(jsonReader, City::class.java)
            city.name = city.name.lowercase(Locale.ROOT) //To make search case insensitive
            cities.add(city) //Add city to cities list
            _trie.addCity(city) // Add city to prefix tree
        }
    }
}