package com.example.login.LoginScreen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.wanderwisely.R

@Composable
fun Header(modifier: Modifier){
    Spacer(modifier = Modifier.size(100.dp))
    val activity = LocalContext.current as Activity
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Wander Wisely",
        modifier = modifier.size(75.dp)
    )
}