package com.andrewsaba.cityfinder.trie

import com.andrewsaba.cityfinder.model.City
/*
 Prefix tree (trie) is a tree-like structure,
 where each node represents a character in a city name.
 This approach is very fast for prefix lookups.
 */

class Trie {
    private val root = TrieNode()

    fun addCity(city: City) = root.addCity(city)

    fun searchCitiesByPrefix(prefix: String): List<City> {
        return root.getAllCities(prefix)
    }

}
