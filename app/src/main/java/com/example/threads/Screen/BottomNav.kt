package com.example.threads.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.threads.Navigation.Routes
import com.example.threads.model.BottomNavItem

@Composable
fun BottomNav(navController: NavHostController) {

    val navController1 = rememberNavController()
    
    Scaffold(bottomBar = { MyBottomBar(navController = navController1) }) { innerPadding ->

        NavHost(navController = navController1, startDestination = Routes.Home.routes,
            modifier = Modifier.padding(innerPadding)
            ){
            composable(Routes.Home.routes){
                Home()
            }
            composable(Routes.Search.routes) {
                Search()
            }
            composable(Routes.Notification.routes) {
                Notification()
            }
            composable(Routes.profile.routes) {
                Profile(navController)
            }
            composable(Routes.AddThreads.routes) {
                AddThreads()
            }
        }
        
    }
    
}

@Composable
fun MyBottomBar(navController: NavHostController){

    val backStackEntry = navController.currentBackStackEntry
    val currentRoute = backStackEntry?.destination?.route

    val list = listOf(

        BottomNavItem(
            "home",
            Routes.Home.routes,
            Icons.Rounded.Home
        ),
        BottomNavItem(
            "Search",
            Routes.Search.routes,
            Icons.Rounded.Search
        ),
        BottomNavItem(
            "Add Threads",
            Routes.AddThreads.routes,
            Icons.Rounded.Add
        ),
        BottomNavItem(
            "Notification",
            Routes.Notification.routes,
            Icons.Rounded.Notifications
        ),
        BottomNavItem(
            "Profile",
            Routes.profile.routes,
            Icons.Rounded.Person
        )
    )

BottomAppBar {
    list.forEach{
        val selected  = it.route == backStackEntry?.destination?.route

        NavigationBarItem(selected = selected,
            onClick = {
                navController.navigate(it.route){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                }
            },
            icon = { 
                Icon(imageVector = it.icon, contentDescription = it.title)
            })
    }
}
}
