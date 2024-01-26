package com.example.wanderwisely.viewmodel

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.wanderwisely.model.Coordinate
import com.example.wanderwisely.model.HikingRoute
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.util.concurrent.Flow

class DatabaseController(private val navController: NavHostController) : ViewModel() {

    private val database = Firebase.database("https://wanderwisely-348b4-default-rtdb.europe-west1.firebasedatabase.app")
    private val routeRef = database.getReference("routes")

    val routes: MutableLiveData<List<HikingRoute>> = MutableLiveData()

    init {
        // Llama a la función para cargar los datos en la inicialización
        fetchRoutes()
    }

    fun fetchRoutes(){
        routeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(snapshotError: DatabaseError) { }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot!!.children
                val routesList = mutableListOf<HikingRoute>()
                children.forEach {
                    var name = it.child("name").value as String
                    var description = it.child("description").value as String

                    //Coords
                    var coordinates = mutableListOf<Coordinate>()
                    var coords = it.child("coords").children
                    coords.forEach {
                        var lat = it.child("lat").value as Double
                        var long = it.child("long").value as Double
                        Log.i("FIREBASE REALTIME", "Coordinate: " + lat + ", " + long)
                        coordinates.add(Coordinate(lat, long))
                    }
                    var stars = it.child("stars").children
                    var avgStars = 0.0;
                    var totalReviewers = 0;
                    if(stars.iterator().hasNext()){
                        stars.forEach {
                            totalReviewers++;
                            avgStars += it.value as Long;
                        }
                        avgStars = avgStars / totalReviewers
                    }

                    Log.i("FIREBASE REALTIME", "Read route " + name + " with stars " + avgStars)

                    routesList.add(HikingRoute(name, description, coordinates, avgStars))
                }
                routes.value = routesList
            }
        })
    }

    //Uploads the rating for a hiking route
    fun rateHikingRoute(route: HikingRoute, stars: Int){
        routeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(snapshotError: DatabaseError) { }

            override fun onDataChange(snapshot: DataSnapshot) {

                var ref = routeRef.child(route.name).child("stars")
                var userId = FirebaseAuth.getInstance().currentUser?.uid

                if(userId != null){
                    ref.child(userId).setValue(stars)
                }

            }
        })
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        navController.navigate("LoginScreen")
    }


}