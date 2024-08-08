package com.andrewsaba.cityfinder.model

data class City(val country: String, var name: String, private val _id: Int, val coord: Coordinate)
