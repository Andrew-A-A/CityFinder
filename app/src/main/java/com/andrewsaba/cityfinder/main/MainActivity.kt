package com.andrewsaba.cityfinder.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.andrewsaba.cityfinder.R
import com.andrewsaba.cityfinder.adapter.CitiesListAdapter
import com.andrewsaba.cityfinder.databinding.ActivityMainBinding
import com.andrewsaba.cityfinder.model.City

class MainActivity : AppCompatActivity() {

    // View model object
    private lateinit var viewModel: MainViewModel

    // Binding object
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Binding object initialization
        binding= ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //View model initalization
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        //Cities list initalization (UI)
        if (viewModel.isDataLoaded)
         binding.citiesList.adapter=viewModel.getCitiesListAdapter()

        //Set observer for search result
        viewModel.searchResults.observe(this) { results ->
            displaySearchResults(results)
        }
    }
    // Display search results in the UI (if any)
    private fun displaySearchResults(results: List<City>?) {
        if (results != null) {
            val adapter = CitiesListAdapter(results)
            binding.citiesList.adapter = adapter
        }
        else{
            binding.citiesList.adapter=null
        }
    }
}

