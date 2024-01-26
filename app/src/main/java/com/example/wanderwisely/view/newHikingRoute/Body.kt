package com.example.wanderwisely.view.newHikingRoute

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wanderwisely.model.Coordinate
import com.example.wanderwisely.model.Line
import com.example.wanderwisely.model.Routes
import com.example.wanderwisely.viewmodel.NewRouteViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Polyline

@Composable
fun Body(modifier: Modifier, viewModel: NewRouteViewModel){

    val name: String by viewModel.name.observeAsState(initial = "")
    val description: String by viewModel.description.observeAsState(initial = "")
    val coordenates: List<Coordinate> by viewModel.coords.observeAsState(listOf<Coordinate>())

    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Name(onTextFieldChanged = { viewModel.onNameChanged(it) })
        Spacer(modifier = Modifier.size(5.dp))
        Description(onTextFieldChanged = { viewModel.onDescriptionChanged(it) })
        Spacer(modifier = Modifier.size(5.dp))
        RouteMap(viewModel){ viewModel.onCoordsChanged(it) }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Name(onTextFieldChanged: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { onTextFieldChanged(it)
            text = it},
        modifier = Modifier
            .border(1.dp, Color(0xFF005897), RoundedCornerShape(5.dp))
            .fillMaxWidth(),
        label={ Text(text="Nombre de la ruta", fontSize = 16.sp) },
        placeholder = { Text(text = "Nombre de la ruta") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF000000),
            containerColor = Color(0xFFFFFFFF),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Description(onTextFieldChanged: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { onTextFieldChanged(it)
            text = it},
        modifier = Modifier
            .border(1.dp, Color(0xFF005897), RoundedCornerShape(5.dp))
            .fillMaxWidth(),
        label={ Text(text="Descripción de la ruta", fontSize = 16.sp) },
        placeholder = { Text(text = "Descripción de la ruta") },
        maxLines = 6,
        singleLine = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF000000),
            containerColor = Color(0xFFFFFFFF),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun RouteMap(viewModel: NewRouteViewModel, onCoordAdded: (List<Coordinate>) -> Unit){
    var coords = mutableListOf<Coordinate>()

    var clickedCoord by remember { mutableStateOf(Coordinate(0.0, 0.0)) }
    var lines by remember { mutableStateOf(createPairList(coords)) }

    //The initial camera position will the Madrid
    val cameraPosition = CameraPositionState(
        position = CameraPosition(
            LatLng(40.416775, -3.703790),
            5f,
            0f,
            0f
        )
    )

    GoogleMap (
        Modifier.fillMaxSize(),
        cameraPositionState = cameraPosition,
        properties = MapProperties(mapType = MapType.SATELLITE),
        onMapClick = {latLng ->
            Log.i("GOOGLE MAP", "Clicked coords: " + latLng.latitude + latLng.longitude)
            clickedCoord = Coordinate(latLng.latitude, latLng.longitude)
            coords.add(clickedCoord)

            lines = createPairList(coords)

            onCoordAdded(coords)
        }
    ){
        lines.forEach {
            Polyline(
                points = listOf(LatLng(it.init.lat, it.init.long),
                    LatLng(it.end.lat, it.end.long)),
                color = Color.Red)
        }
    }

}

fun createPairList(listaCoordenadas: List<Coordinate>): List<Line> {
    val listaLineas = mutableListOf<Line>()
    if(listaCoordenadas.size > 0){
        for (i in 0 until listaCoordenadas.size - 1) {
            val puntoInicio = listaCoordenadas[i]
            val puntoFin = listaCoordenadas[i + 1]

            val linea = Line(puntoInicio, puntoFin)
            listaLineas.add(linea)
        }
    }

    return listaLineas
}
