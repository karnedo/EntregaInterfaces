package com.example.wanderwisely.view.hiking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wanderwisely.model.HikingRoute
import com.example.wanderwisely.viewmodel.DatabaseController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.wanderwisely.R
import com.example.wanderwisely.model.Coordinate
import com.example.wanderwisely.model.Line
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState

@Composable
fun HikingScreen(routeName: String, viewModel: DatabaseController = viewModel()){

    val routes by viewModel.routes.observeAsState(initial = emptyList())
    val route = routes.find{it.name == routeName}

    if (route != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.lazy_background),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            // Título
            Text(
                text = route.name,
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 20.dp, end = 20.dp)
            )

            Divider(thickness = 1.dp, color = Color.White, modifier = Modifier.padding(14.dp))

            // Descripción
            Text(
                text = route.description,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, end = 20.dp, start = 20.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val options = GoogleMapOptions()
                options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                    .compassEnabled(false)
                    .rotateGesturesEnabled(true)
                    .tiltGesturesEnabled(false)

                var lines = createPairList(route.coords)

                val cameraPosition = CameraPositionState(
                    position = CameraPosition(
                        LatLng(lines.get(0).init.lat, lines.get(0).init.long),
                        13f,
                        0f,
                        0f
                    ))

                var clickedLatLng: LatLng = LatLng(0.34, 0.65);
                GoogleMap (
                    Modifier.fillMaxSize(),
                    cameraPositionState = cameraPosition,
                    properties = MapProperties(mapType = MapType.SATELLITE),
                    //This is done for testing purposes. This will go into the MarkerScreen
                    onMapClick = {latLng ->
                        clickedLatLng = latLng
                        Log.i("GOOGLE MAP", "Clicked coords: " + clickedLatLng.latitude + clickedLatLng.longitude)
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
        }
    }


}

fun createPairList(listaCoordenadas: List<Coordinate>): List<Line> {
    val listaLineas = mutableListOf<Line>()

    for (i in 0 until listaCoordenadas.size - 1) {
        val puntoInicio = listaCoordenadas[i]
        val puntoFin = listaCoordenadas[i + 1]

        val linea = Line(puntoInicio, puntoFin)
        listaLineas.add(linea)
    }

    return listaLineas
}