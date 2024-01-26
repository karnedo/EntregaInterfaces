package com.example.wanderwisely.model

data class HikingRoute(val name: String,
                        val description: String,
                        val coords: List<Coordinate>,
                        val stars: Double = 2.5)