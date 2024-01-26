package com.example.wanderwisely

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.LoginScreen.LoginScreen
import com.example.wanderwisely.model.Routes
import com.example.wanderwisely.ui.theme.WanderWiselyTheme
import com.example.wanderwisely.view.hiking.HikingScreen
import com.example.wanderwisely.view.main.MainScreen
import com.example.wanderwisely.view.newHikingRoute.NewHikingRouteScreen
import com.example.wanderwisely.viewmodel.DatabaseController
import com.example.wanderwisely.viewmodel.LoginViewModel
import com.example.wanderwisely.viewmodel.NewRouteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanderWiselyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    val firebaseViewModel: DatabaseController = DatabaseController(navigationController)
                    val hikingViewModel: NewRouteViewModel = NewRouteViewModel(navigationController)

                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.LoginScreen.route
                    ){
                        composable(route = Routes.LoginScreen.route) { LoginScreen(LoginViewModel(navigationController))}
                        composable(route = Routes.MainScreen.route) { MainScreen(navigationController, firebaseViewModel) }
                        composable(route = Routes.HikingScreen.route + "/{name}") {backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name")
                            if (name != null) {
                                HikingScreen(routeName = name, firebaseViewModel)
                            }
                        }
                        composable(route = Routes.NewHikingRouteScreen.route) { NewHikingRouteScreen(navigationController, hikingViewModel) }
                    }

                }
            }
        }
    }
}