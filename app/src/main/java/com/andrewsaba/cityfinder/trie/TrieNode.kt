package com.andrewsaba.cityfinder.trie

import com.andrewsaba.cityfinder.model.City
/*
 TrieNode used to represent a single node in the prefix tree.
 TrieNode is the building unit of the Trie data structure.
 */


class TrieNode {
    private val children = mutableMapOf<Char, TrieNode>()
    private var isEndOfWord = false
    private val cities = mutableListOf<City>() // Store cities associated with this node

    fun addCity(city: City, wordIndex: Int = 0) {
        if (wordIndex == city.name.length) {
            isEndOfWord = true
            cities.add(city)
            return
        }
        val char = city.name[wordIndex]
        children.getOrPut(char) { TrieNode() }.addCity(city, wordIndex + 1)
    }

    fun getAllCities(prefix: String, wordIndex: Int = 0): List<City> {
        if (wordIndex == prefix.length) {
            return cities + children.values.flatMap { it.getAllCities(prefix, wordIndex) }
        } else {
            val char = prefix[wordIndex]
            val childNode = children[char] ?: return emptyList() // Early termination
            return childNode.getAllCities(prefix, wordIndex + 1)
        }
    }
}