package com.example.wanderwisely.model

sealed class Routes(val route: String){
    object LoginScreen : Routes("LoginScreen")
    object MainScreen : Routes("MainScreen")
    object HikingScreen : Routes("HikingScreen/{name}")
    object NewHikingRouteScreen : Routes("NewHikingRouteScreen")
}
