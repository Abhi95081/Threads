package com.example.threads.Navigation

sealed class Routes(val routes:String) {

    object Home : Routes("home")
    object Notification : Routes("notification")
    object profile : Routes("profile")
    object Search : Routes("search")
    object Splash : Routes("splash")
    object AddThreads : Routes("add_threads")
    object BottomNav : Routes("bottom_nav")

}