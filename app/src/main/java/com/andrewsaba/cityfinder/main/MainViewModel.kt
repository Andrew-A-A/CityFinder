package com.andrewsaba.cityfinder.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andrewsaba.cityfinder.trie.Trie
import com.andrewsaba.cityfinder.adapter.CitiesListAdapter
import com.andrewsaba.cityfinder.model.City
import com.google.gson.Gson
import kotlinx.coroutines.launch


class MainViewModel(application: Application):AndroidViewModel(application) {
    //Check if cities list is loaded
     var isDataLoaded=false
     var isAdapterInitialized=false

    private lateinit var citiesListAdapter: CitiesListAdapter

    //String entered by user to search for cities
    val searchString=""

    //Search results Live data
    private val _searchResults = MutableLiveData<List<City>?>()
    val searchResults: MutableLiveData<List<City>?> = _searchResults

    //Trie (prefix tree)
    private val _trie = MutableLiveData<Trie>()

    //Load cities list from JSON file
    private val _cities: List<City> =try {
        val jsonString= application.assets.open("cities.json")
            .bufferedReader().use { it.readText() }   //Load JSON file from assets
        Gson().fromJson(jsonString,Array<City>::class.java).toList() //Parse JSON to list of cities
    } catch (exception: Exception){
        throw exception
    } finally {
        isDataLoaded=true
    }

    //Return cities list adapter
    fun getCitiesListAdapter(): CitiesListAdapter {
        if (!isAdapterInitialized) {
            initializeTrie(_cities)
            citiesListAdapter = CitiesListAdapter(_cities)
            isAdapterInitialized=true
        }
        return citiesListAdapter
    }

    // Initialize trie with cities list (asynchronously)
     private fun initializeTrie(citiesList: List<City>) {
        viewModelScope.launch { //Launch coroutine
            val trie= Trie()
            for (city in citiesList){
                trie.addCity(city)
            }
            _trie.postValue(trie)
        }
    }

    //Search in trie for a given prefix  (asynchronously)
    fun searchCitiesByPrefix(prefix: String) {
        viewModelScope.launch { //Launch coroutine
            val results = _trie.value?.searchCitiesByPrefix(prefix)
            _searchResults.postValue(results)
        }
    }
}