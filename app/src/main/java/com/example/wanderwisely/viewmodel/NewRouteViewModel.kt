package com.example.wanderwisely.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.wanderwisely.model.Coordinate
import com.example.wanderwisely.model.Routes
import com.google.firebase.Firebase
import com.google.firebase.database.database

class NewRouteViewModel(private val navController: NavHostController): ViewModel() {

    private val database = Firebase.database("https://wanderwisely-348b4-default-rtdb.europe-west1.firebasedatabase.app")
    private val routeRef = database.getReference("routes")

    private val _name = MutableLiveData<String>();
    val name: LiveData<String> = _name;

    private val _description = MutableLiveData<String>();
    val description: LiveData<String> = _description;

    private val _coords = MutableLiveData<List<Coordinate>>();
    val coords: LiveData<List<Coordinate>> = _coords;

    fun onCoordsChanged(list: List<Coordinate>){
        _coords.value = list
    }

    fun onNameChanged(name: String){
        _name.value = name
    }

    fun onDescriptionChanged(description: String){
        _description.value = description
    }

    fun uploadRoute(){
        Log.i("FIREBASE REALTIME", "Uploading route...")
        val routeRoot = routeRef.child("" + _name.value)
        routeRoot.child("name").setValue(_name.value)
        routeRoot.child("description").setValue(_description.value)

        var i = 0;

        _coords.value?.forEach {
            val coordRoot = routeRoot.child("coords").child("" + i)
            coordRoot.child("lat").setValue(it.lat)
            coordRoot.child("long").setValue(it.long)
            i = i + 1;
        }
        navController.navigate(Routes.MainScreen.route)
    }

}