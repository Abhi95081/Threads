package com.example.threads.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.threads.Screen.AddThreads
import com.example.threads.Screen.Home
import com.example.threads.Screen.Notification
import com.example.threads.Screen.Profile
import com.example.threads.Screen.Search
import com.example.threads.Screen.Splash

@Composable
fun Navgraph(navController: NavHostController) {

    NavHost(navController = navController,
        startDestination = Routes.Splash.routes) {

        composable(Routes.Splash.routes) {
            Splash()
        }
        composable(Routes.Home.routes) {
            Home()
        }
        composable(Routes.Search.routes) {
            Search()
        }
        composable(Routes.Notification.routes) {
            Notification()
        }
        composable(Routes.profile.routes) {
            Profile()
        }
        composable(Routes.AddThreads.routes) {
            AddThreads()
        }

    }

}