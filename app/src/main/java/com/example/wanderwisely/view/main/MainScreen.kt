package com.example.wanderwisely.view.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wanderwisely.R
import com.example.wanderwisely.model.Coordinate
import com.example.wanderwisely.model.HikingRoute
import com.example.wanderwisely.model.Routes
import com.example.wanderwisely.viewmodel.DatabaseController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigationController: NavHostController, viewModel: DatabaseController = viewModel()){

    val routes by viewModel.routes.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Wander Wisely",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }

                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.signOut()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Back",
                            modifier = Modifier.scale(scaleX = -1f, scaleY = 1f)
                        )
                    }
                },
                modifier = Modifier.height(40.dp)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.lazy_background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = 30.dp)
        ) {


            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            ) {
                items(routes.size) { i ->
                    HikingCard(routes.get(i), navigationController, viewModel)
                }
            }

            // Floating Action Button
            FloatingActionButton(
                onClick = {
                    navigationController.navigate(Routes.NewHikingRouteScreen.route)
                },
                modifier = Modifier
                    .padding(24.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomStart),
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            )
        }

        LaunchedEffect(viewModel) {
            viewModel.fetchRoutes()
        }
    }
}