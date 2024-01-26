package com.example.wanderwisely.view.main

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wanderwisely.R
import com.example.wanderwisely.model.HikingRoute
import com.example.wanderwisely.model.Routes
import com.example.wanderwisely.viewmodel.DatabaseController
import kotlin.math.roundToInt

@Composable
fun HikingCard(route: HikingRoute, navigationController: NavHostController, viewModel: DatabaseController = viewModel()) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .size(width = 340.dp, height = 175.dp)
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = route.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            // Display only a portion of the description if it exceeds 80 characters
            val shortDescription = if (route.description.length > 80) {
                route.description.substring(0, 80) + "..."
            } else {
                route.description
            }
            Text(
                text = shortDescription,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 12.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            ) {
                val ratingState = remember { mutableIntStateOf(route.stars.roundToInt()) }

                UserRatingBar(
                    ratingState = ratingState,
                    modifier = Modifier.weight(1f),
                    viewModel = viewModel,
                    route = route)

                Button(
                    onClick = {
                        navigationController.navigate("${Routes.HikingScreen.route}/${route.name}")
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .padding(start = 6.dp)
                ) {
                    Text(text = "Ver ruta")
                }
            }
        }
    }
}

@Composable
fun UserRatingBar(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    ratingState: MutableState<Int> = remember { mutableIntStateOf(0) },
    ratingIconPainter: Painter = painterResource(id = R.drawable.ic_star),
    selectedColor: Color = Color(0xFFFFD700),
    unselectedColor: Color = Color(0xFFA2ADB1),
    viewModel: DatabaseController,
    route: HikingRoute
) {
    Row(modifier = modifier.wrapContentSize()) {
        for (value in 1..5) {
            StarIcon(
                size = size,
                painter = ratingIconPainter,
                ratingValue = value,
                ratingState = ratingState,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                viewModel = viewModel,
                route = route
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StarIcon(
    size: Dp,
    ratingState: MutableState<Int>,
    painter: Painter,
    ratingValue: Int,
    selectedColor: Color,
    unselectedColor: Color,
    viewModel: DatabaseController,
    route: HikingRoute
) {
    val tint by animateColorAsState(
        targetValue = if (ratingValue <= ratingState.value) selectedColor else unselectedColor,
        label = ""
    )

    Icon(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        ratingState.value = ratingValue
                        //When a star is touched, upload the rating

                        viewModel.rateHikingRoute(route, ratingValue)

                    }
                }
                true
            },
        tint = tint
    )
}