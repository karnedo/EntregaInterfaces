package com.example.wanderwisely.view.newHikingRoute

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wanderwisely.R
import com.example.wanderwisely.model.Routes
import com.example.wanderwisely.viewmodel.NewRouteViewModel
import kotlinx.coroutines.launch

@Composable
fun NewHikingRouteScreen(navigationController: NavHostController, viewModel: NewRouteViewModel){

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.lazy_background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()){
            Header(Modifier.padding(top = 16.dp, start = 20.dp))
            Divider(thickness = 1.dp, color = Color.White, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.weight(1f))
            Body(Modifier.fillMaxSize().padding(16.dp), viewModel)
        }

        FloatingActionButton(
            onClick = {
                coroutineScope.launch { viewModel.uploadRoute() }
            },
            modifier = Modifier
                .padding(32.dp)
                .size(56.dp)
                .clip(CircleShape)
                .align(Alignment.BottomStart),
            content = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check"
                )
            }
        )
    }

}